package main.uade.edu.ar.dao;

import main.uade.edu.ar.model.Usuario;
import java.util.List;

public interface IUsuarioDao {
    List<Usuario> getAll() throws Exception;
    void save(Usuario usuario) throws Exception;
    boolean update(Usuario usuario) throws Exception;
    boolean delete(int id) throws Exception;
    Usuario search(int id) throws Exception;
}

