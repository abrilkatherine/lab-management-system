package main.uade.edu.ar.dao;

import main.uade.edu.ar.model.Paciente;
import java.util.List;

public interface IPacienteDao {
    List<Paciente> getAll() throws Exception;
    void save(Paciente paciente) throws Exception;
    boolean update(Paciente paciente) throws Exception;
    boolean delete(int id) throws Exception;
    Paciente search(int id) throws Exception;
}

