package main.uade.edu.ar.dao;

import main.uade.edu.ar.model.Peticion;
import java.util.List;

public interface IPeticionDao {
    List<Peticion> getAll() throws Exception;
    void save(Peticion peticion) throws Exception;
    boolean update(Peticion peticion) throws Exception;
    boolean delete(int id) throws Exception;
    Peticion search(int id) throws Exception;
}

