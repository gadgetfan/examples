package com.refreshgames.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pools;
import com.refreshgames.model.actor.robot.RobotPart;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.world.BaseWorld;
import com.refreshgames.model.listeners.ApertureCollisionListener;
import com.refreshgames.model.listeners.CollisionListener;
import com.refreshgames.model.modeldata.Collisions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * World emulator for game
 */
public class GameWorld extends BaseWorld { //TODO: remove
    public static final float SCALE = 1f / 100;
    public static Vector2 NORMAL_GRAVITY = new Vector2(0, -9.8f);
    public static Vector2 ZERO_GRAVITY = new Vector2(0, 0);

    /**
     * set of contacts, that currently are processed. Is used to fire only ONE event on collision.
     * TODO: optimisation add and use custom field Contact.isProcessed
     */
    private Set<Contact> processedContacts = new HashSet<Contact>();

    public GameWorld(Vector2 gravity) {
        super(gravity);
    }

    /**
     * center of all joints (now returns correct data only to RevoluteJoints)
     * @return
     */
    public Vector2 calcJointsCenter(Vector2 jointsCenter) {
        jointsCenter.set(0, 0);
        final int jointsCnt = world.getJointCount();
        Iterator<Joint> jointIterator = world.getJoints();
        while (jointIterator.hasNext()) {
            Joint joint = jointIterator.next();
            Vector2 jointCenter = joint.getBodyA().getWorldCenter();
            jointsCenter.set(jointsCenter.x + jointCenter.x / jointsCnt,
                    jointsCenter.y + jointCenter.y / jointsCnt);
        }

        return jointsCenter;
    }

    @Override
    protected void addContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (Collisions.isApertureCollision(contact)) {
                    fireApertureCollisionEvent(contact, true);
                } else {
                    BaseActor collidedActor = null;
                    BaseActor targetActor = null;
                    if (contact.getFixtureA().isSensor()) {
                        targetActor = (BaseActor) contact.getFixtureA().getBody().getUserData();
                        collidedActor = (BaseActor) contact.getFixtureB().getBody().getUserData();
                    } else if ((contact.getFixtureB().isSensor())) {
                        targetActor = (BaseActor) contact.getFixtureB().getBody().getUserData();
                        collidedActor = (BaseActor) contact.getFixtureA().getBody().getUserData();
                    }

                    if (targetActor != null) {
                        CollisionListener.CollisionEvent event = Pools.obtain(CollisionListener.CollisionEvent.class);
                        event.setCollidedActor(collidedActor);
                        event.setImpulse(0);
                        targetActor.fire(event);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (Collisions.isApertureCollision(contact)) {
                    fireApertureCollisionEvent(contact, false);
                } else {
                    processedContacts.remove(contact);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                if (Collisions.isApertureCollision(contact)) {
                } else {
                    if (!processedContacts.contains(contact)) {
                        processedContacts.add(contact);

                        //calc impulse
                        float normalImpulse = 0;
                        //TODO: optimization use impulse.getNormalImpulses()[0] * 2 ?
                        //TODO: why very big impulses if I use (impulse.getNormalImpulses().length) instead of 1
                        for (int i = 0; i < 1; ++i) {
                            normalImpulse += Math.abs(impulse.getNormalImpulses()[i]);
                        }
                        BaseActor actor1 = (BaseActor) contact.getFixtureA().getBody().getUserData();
                        BaseActor actor2 = (BaseActor) contact.getFixtureB().getBody().getUserData();
                        CollisionListener.CollisionEvent event1 = Pools.obtain(CollisionListener.CollisionEvent.class);
                        event1.setCollidedActor(actor2);
                        event1.setImpulse(normalImpulse);
                        actor1.fire(event1);

                        CollisionListener.CollisionEvent event2 = Pools.obtain(CollisionListener.CollisionEvent.class);
                        event2.setCollidedActor(actor1);
                        event2.setImpulse(normalImpulse);
                        actor2.fire(event2);
                    }
                }
            }
        });
    }

    private void fireApertureCollisionEvent(Contact contact, boolean begin) {

        ApertureCollisionListener.ApertureCollisionEvent event =
                Pools.obtain(ApertureCollisionListener.ApertureCollisionEvent.class);
        Fixture activeFixture = null;
        Fixture passiveFixture = null;
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();
        if (userDataA instanceof RobotPart && ((RobotPart) userDataA).isActive()) {
            activeFixture = contact.getFixtureA();
            passiveFixture = contact.getFixtureB();
        } else if (userDataB instanceof RobotPart && ((RobotPart) userDataB).isActive()) {
            activeFixture = contact.getFixtureB();
            passiveFixture = contact.getFixtureA();
        }

        if (activeFixture != null && passiveFixture != null) {
            event.setActiveFixture(activeFixture);
            event.setPassiveFixture(passiveFixture);
            event.setBegin(begin);

            ((RobotPart) activeFixture.getBody().getUserData()).fire(event);
        }
    }
}
