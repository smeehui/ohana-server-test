package vn.tg.ohana.services;

import vn.tg.ohana.repository.model.Category;
import vn.tg.ohana.repository.model.Gender;

import java.util.List;

public interface GenderService {
    List<Gender> findAll();
}
