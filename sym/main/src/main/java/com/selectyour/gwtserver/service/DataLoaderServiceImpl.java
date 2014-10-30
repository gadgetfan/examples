package com.selectyour.gwtserver.service;

import com.selectyour.gwtclient.rpc.service.DataLoaderService;

/**
 * Loads data from volhovec.ru
 * Additionally to loading is needed:
 * 1) in DB set coverings.color_type_id for every covering
 * 2) in DB set series.description, series.style_id for every serie
 * 3) copy files from PATH_TO_DB_IMAGES
 */
public class DataLoaderServiceImpl extends BaseSpringGwtServlet implements DataLoaderService {
    //set this constants before show
    /*static private final String PATH_TO_DB_IMAGES = "d:/temp";
    static private final Long DEFAULT_COLOR_TYPE_ID = 1L;
    static private final String DEFAULT_NAME = "нет";
    static private final String DEFAULT_DESCRIPTION = "";

    @Autowired
    private SerieService serieService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private CoveringService coveringService;
    @Autowired
    private GlazingService glazingService;
    @Autowired
    private DecorService decorService;
    @Autowired
    private PictureService pictureService;

    private Serie serie;
    private Model model;
    private Covering covering;
    private Glazing glazing;
    private Decor decor;

    private Properties dataProperties;

    public void loadDataVolhovec(String data) throws DataLoaderException {
        try {
            loadProperties(data);

            processSerie();
            processModel();
            processCovering();
            processGlazing();
            processDecor();

            processDoor();

        } catch (Throwable e) {
            throw new DataLoaderException(e);
        }
    }

    private void processSerie() {
        String name = getRequiredProperty("Serie.name");
        serie = serieService.findOneByName(name);
        if (serie == null) {
            serie = new Serie();
            serie.setName(name);
            serie.setDescription(DEFAULT_DESCRIPTION);
            serie = serieService.save(serie);
        }
    }

    private void processModel() {
        String name = getRequiredProperty("Model.name");
        model = modelService.findOneByName(name);
        if (model == null) {
            model = new Model();
            model.setName(name);
            model = modelService.save(model);
        }
    }

    private void processCovering() throws IOException {
        String name = getRequiredProperty("Covering.name");
        covering = coveringService.findOneByName(name);
        if (covering == null) {
            covering = new Covering();
            covering.setName(name);
            covering.setColorTypeId(DEFAULT_COLOR_TYPE_ID);
            covering = coveringService.save(covering);

            saveFile(getRequiredProperty("Covering.url"), PATH_TO_DB_IMAGES + coveringService.getImagePath(covering));
        }
    }

    private void processGlazing() {
        String name = dataProperties.getProperty("Glazing.name", DEFAULT_NAME);
        glazing = glazingService.findOneByName(name);
        if (glazing == null) {
            glazing = new Glazing();
            glazing.setName(name);
            glazing = glazingService.save(glazing);
        }
    }

    private void processDecor() {
        String name = dataProperties.getProperty("Decor.name", DEFAULT_NAME);
        decor = decorService.findOneByName(name);
        if (decor == null) {
            decor = new Decor();
            decor.setName(name);
            decor = decorService.save(decor);
        }
    }

    private void processDoor() throws IOException {
        Double price = Double.valueOf(getRequiredProperty("Door.price"));
        Picture picture = pictureService.findOne(serie.getId(), model.getId(), covering.getId(), glazing.getId(), decor.getId());
        if (picture == null) {
            picture = new Picture();
            picture.setSerieId(serie.getId());
            picture.setModelId(model.getId());
            picture.setCoveringId(covering.getId());
            picture.setGlazingId(glazing.getId());
            picture.setDecorId(decor.getId());
            picture.setPrice(price);$
            picture = pictureService.save(picture);

            saveFile(getRequiredProperty("Door.url"), PATH_TO_DB_IMAGES + pictureService.getImagePath(picture));
        }
    }

    private void loadProperties(String data) throws IOException {
        dataProperties = new Properties();
        dataProperties.load(new StringReader(data));
    }

    private String getRequiredProperty(String propertyName) {
        String result = dataProperties.getProperty(propertyName);
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException(String.format("No property %s", propertyName));
        }

        return result;
    }

    private void saveFile(String sUrl, String fileName) throws IOException {
        byte[] buffer = new byte[8 * 1024];

        URL url = new URL(sUrl);
        InputStream input = url.openConnection().getInputStream();
        try {
            OutputStream output = new FileOutputStream(fileName);
            try {
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
    }*/
}
