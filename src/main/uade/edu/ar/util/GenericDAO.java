package main.uade.edu.ar.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public abstract class GenericDAO<T> {
    final Class<T> clase;
    protected File archivo;

    public GenericDAO(Class<T> clase, String file) throws Exception {
        this.clase = clase;

        this.archivo = new File(file);
        this.archivo.createNewFile();
    }

    public List<T> getAll(Class<T> clase) throws Exception {
        List<T> list = new ArrayList<T>();
        FileReader f = null;
        BufferedReader b = null;
        Gson g = new Gson();
        String line = "";

        try {
            f = new FileReader(archivo);
            b = new BufferedReader(f);

            while ((line = b.readLine()) != null && !line.equals("")) {
                JsonElement jsonElement = JsonParser.parseString(line);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                list.add(g.fromJson(jsonObject, clase));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
        return list;
    }

    public List<T> getAll() throws Exception {
        return getAll(clase);
    }

    public void saveAll(List<T> list) throws Exception {
        Gson g = new Gson();
        String texto = "";
        for (Object obj : list) {
            texto = texto.concat(g.toJson(obj));
            texto = texto.concat(System.lineSeparator());
        }

        FileWriter fileWriter = null;
        BufferedWriter bwEscritor = null;
        try {
            fileWriter = new FileWriter(archivo);
            bwEscritor = new BufferedWriter(fileWriter);
            bwEscritor.write(texto);
        } finally {
            if (bwEscritor != null) {
                try {
                    bwEscritor.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }

    public void save(T obj) throws Exception {
        Gson g = new Gson();
        String texto = g.toJson(obj);
        texto = texto.concat(System.lineSeparator());
        FileWriter fileWriter = null;
        BufferedWriter bwEscritor = null;
        try {
            fileWriter = new FileWriter(archivo, true);
            bwEscritor = new BufferedWriter(fileWriter);
            bwEscritor.write(texto);
        } finally {
            if (bwEscritor != null) {
                try {
                    bwEscritor.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }

    public int getLastInsertId() throws Exception {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(archivo));
            String last = "";
            String line;
            String index = "0";

            while ((line = input.readLine()) != null) {
                last = line;
            }

            if (!last.isEmpty()) {
                JsonElement jsonElement = JsonParser.parseString(last);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                index = jsonObject.get("id").toString();
            }
            return Integer.parseInt(index);

        } catch (Exception e) {
            return 0;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }

    public boolean delete(int id) throws Exception {
        boolean wasDeleted = false;
        BufferedReader b = null;
        FileOutputStream fileOut = null;
        try {
            b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = b.readLine()) != null) {
                JsonElement jsonElement = JsonParser.parseString(line);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (Integer.parseInt(jsonObject.get("id").toString()) != id) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                } else {
                    wasDeleted = true;
                }
            }
            String inputStr = inputBuffer.toString();

            System.out.println(inputStr);

            fileOut = new FileOutputStream(archivo);
            fileOut.write(inputStr.getBytes());

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
        return wasDeleted;
    }

    public boolean update(T obj) throws Exception {
        boolean wasUpdate = false;
        BufferedReader b = null;
        FileOutputStream fileOut = null;
        try {
            b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            Gson g = new Gson();

            int objId = getObjectId(obj);

            while ((line = b.readLine()) != null) {
                JsonElement jsonElement = JsonParser.parseString(line);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (Integer.parseInt(jsonObject.get("id").toString()) == objId) {
                    line = g.toJson(obj);
                    wasUpdate = true;
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            String inputStr = inputBuffer.toString();

            System.out.println(inputStr);

            fileOut = new FileOutputStream(archivo);
            fileOut.write(inputStr.getBytes());

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
        return wasUpdate;
    }

    private int getObjectId(T obj) throws Exception {
        java.lang.reflect.Method getIdMethod = obj.getClass().getMethod("getId");
        return (Integer) getIdMethod.invoke(obj);
    }

    public T search(int id) throws FileNotFoundException {
        return search(id, clase);
    }

    public T search(int id, Class<T> clase) throws FileNotFoundException {
        BufferedReader b = null;
        String line;
        Gson g = new Gson();
        Boolean flag = false;

        try {
            b = new BufferedReader(new FileReader(archivo));
            while ((line = b.readLine()) != null && !flag) {
                JsonElement jsonElement = JsonParser.parseString(line);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (Integer.parseInt(jsonObject.get("id").toString()) == id) {
                    return g.fromJson(jsonObject, clase);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
        return null;
    }
}
