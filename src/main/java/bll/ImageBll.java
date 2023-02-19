package bll;

import dao.ImageDao;
import dao.RentUnitDao;
import model.Image;
import model.RentUnit;

import java.util.ArrayList;

public class ImageBll {
    private ImageDao imageDao;

    public ImageBll() {

        imageDao = new ImageDao();
    }
    public void insertImage(Image image) {

        imageDao.insert(image);
    }
    public void updateImage(Image image) {

        imageDao.update(image);
    }

    public ArrayList<Image> readAllImages() {
        return (ArrayList<Image>) imageDao.readAll();
    }
}
