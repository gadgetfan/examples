package com.refreshgames.model.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.refreshgames.model.actor.Borders;
import com.refreshgames.model.actor.robot.RobotPart;
import com.refreshgames.model.base.scene.BaseActor;
import com.refreshgames.model.base.scene.BaseStage;
import com.refreshgames.view.mediadata.SpriteModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * list of actors with save ability
 */
public class SavedActorList implements Json.Serializable {
    private static final String CLASS = "class";
    private static final String X_CENTER = "x";
    private static final String Y_CENTER = "y";
    private static final String ANGLE_DEGREE = "angle";
    private static final String PLAYER = "player";
    private static final String BORDERS = "borders";
    private static final String BORDERS_TYPE_LIST = "types";

    private static BaseStage STAGE; //TODO: is not thread-safe. Possible need my realization of Json to make safe

    private SaveAble player;
    private SaveAble borders;
    private List<SaveAble> actorList = new ArrayList<SaveAble>();


    public void addActor(SaveAble actor) {
        actorList.add(actor);
    }

    public static SavedActorList loadFromFile(String fileName, BaseStage stage) {
        Json json = new Json();
        STAGE = stage;
        SavedActorList savedActorList = json.fromJson(SavedActorList.class, Gdx.files.internal(fileName));
        savedActorList.sort();

        return savedActorList;
    }

    public static SavedActorList loadFromString(String jsonString, BaseStage stage) {
        Json json = new Json();
        STAGE = stage;
        SavedActorList savedActorList = json.fromJson(SavedActorList.class, jsonString);
        savedActorList.sort();

        return savedActorList;
    }

    public static SavedActorList loadFromStage(GameStage stage) {
        SavedActorList savedActorList = new SavedActorList();
        for (Actor actor : stage.getActors()) {
            if (actor instanceof SaveAble) {
                savedActorList.addActor((SaveAble) actor);
            }
        }
        savedActorList.player = (SaveAble) stage.getPlayer();
        savedActorList.borders = stage.borders;
        savedActorList.sort();

        return savedActorList;
    }

    public void sort() {
        Collections.sort(actorList, ActorsComparator.Z_INDEX_SAVEABLE_COMPARATOR);
    }

    public void saveTo(String fileName) {
        Json json = new Json();
        json.toJson(this, Gdx.files.local(fileName));
        //Gdx.files.local(fileName).writeString(json.toJson(this), false);
    }

    public String getJsonString() {
        Json json = new Json();
        return json.toJson(this);
    }

    @Override
    public void write(Json json) {
        for (int i = 0; i < actorList.size(); ++i) {
            SaveAble actor = actorList.get(i);
            saveActor(json, actor, i);
        }
    }

    @Override
    public void read(Json json, OrderedMap<String, Object> jsonData) {
        ObjectMap.Entries<String, Object> entries = jsonData.entries();
        while (entries.hasNext()) {
            ObjectMap.Entry entry = entries.next();
            OrderedMap<String, Object> paramMap = (OrderedMap<String, Object>) entry.value;
            try {
                Class clazz = Class.forName((String) paramMap.get(CLASS));
                Object actor = null;
                if (RobotPart.class.isAssignableFrom(clazz)) {
                    Constructor constructor = clazz.getConstructor(BaseStage.class,
                            float.class, float.class, float.class);
                    actor = constructor.newInstance(STAGE, paramMap.get(X_CENTER), paramMap.get(Y_CENTER),
                            paramMap.get(ANGLE_DEGREE));

                    if (paramMap.containsKey(PLAYER)) {
                        player = (SaveAble) actor;
                    }

                    addActor((SaveAble) actor);
                } else if (Borders.class.isAssignableFrom(clazz)) {
                    Constructor constructor = clazz.getConstructor(BaseStage.class, List.class);
                    List<Borders.Type> typeList = new ArrayList<Borders.Type>();
                    for (Object type : (Array) paramMap.get(BORDERS_TYPE_LIST)) {
                        typeList.add(Borders.Type.valueOf((String) type));
                    }
                    actor = constructor.newInstance(STAGE, typeList);
                    if (paramMap.containsKey(BORDERS)) {
                        borders = (SaveAble) actor;
                    }
                    addActor(((Borders) borders).getFinish());
                    addActor((SaveAble) actor);
                } else new IllegalStateException("loadActor Unknown actor class: " + clazz.getName());

            } catch (Exception e) {
                throw new RuntimeException("Error creating actor for " + entry.key + "\n" + e.getMessage());
            }
        }
    }


    private void saveActor(Json json, SaveAble actor, int number) {
        json.writeObjectStart(String.format("%s_%d", actor.getClass().getSimpleName(), number));
        json.writeValue(CLASS, actor.getClass().getName());

        if (actor instanceof RobotPart) {
            if (actor.equals(player)) {
                json.writeValue(PLAYER, "");
            }
            SpriteModel spriteModel = (SpriteModel) ((BaseActor) actor).getModelData();
            json.writeValue(X_CENTER, spriteModel.getCenter().x);
            json.writeValue(Y_CENTER, spriteModel.getCenter().y);
            json.writeValue(ANGLE_DEGREE, spriteModel.getAngleDegree());
        } else if (actor instanceof Borders) {
            Borders borders = (Borders) actor;
            json.writeValue(BORDERS_TYPE_LIST, borders.getTypeList());
        } else new IllegalStateException("saveActor Unknown actor class: " + actor.getClass().getName());

        json.writeObjectEnd();
    }

    public List<SaveAble> getActorList() {
        return actorList;
    }

    public SaveAble getPlayer() {
        return player;
    }

    public SaveAble getBorders() {
        return borders;
    }

    static public interface SaveAble {
    }
}
