package main.uade.edu.ar.dao;

import main.uade.edu.ar.model.Sucursal;
import java.util.List;

public interface ISucursalDao {
    List<Sucursal> getAll() throws Exception;
    void save(Sucursal sucursal) throws Exception;
    boolean update(Sucursal sucursal) throws Exception;
    boolean delete(int id) throws Exception;
    Sucursal search(int id) throws Exception;
}

