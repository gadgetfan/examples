package com.refreshgames.other.debug;

//TODO: remove
public class DebugStage {
}/*extends BaseStage {

    public DebugStage(float width, float height, BaseInputProcessor processor) {
        super(width, height, null, processor);
    }

    @Override
    protected void addConstantActors() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void setupControls() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void generateStage() {
        *//*DebugActor debugActor = DebugActor.create(this, 0, 0);
        player = debugActor;
        DebugUtils.init(debugActor);
        addActor(debugActor);*//*
    }

    @Override
    protected BaseWorld generateWorld() {
        GameWorld gameWorld = new GameWorld(GameWorld.NORMAL_GRAVITY);

        return gameWorld;
    }

    @Override
    protected void setupCamera() {
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        super.draw();
    }
}*/
