package Service;
import Entities.Entity;
import Entities.Pacient;
import Entities.Programare;
import Repository.Repo;

import java.util.List;

public class Service<T extends Entity>{
    private final Repo<T> repository;

    public Service(Repo repository) {
        this.repository = repository;
    }

    public void addEntity(T entity) {
        repository.addEntity(entity);
    }

    public void removeEntityByID(int ID) {
        repository.removeEntityByID(ID);
    }

    public T findByID(int ID) {
        return repository.findByID(ID);
    }

    public void updateEntityByID(int idToBeUpdated,T updatedEntity) {
        repository.updateEntity(idToBeUpdated, updatedEntity);
    }

    public List<Pacient> getAllPacienti() {
        return repository.findAll().stream()
                .filter(entity -> entity instanceof Pacient)
                .map(entity -> (Pacient) entity)
                .toList();
    }

    public List<Programare> getAllProgramari() {
        return repository.findAll().stream()
                .filter(entity -> entity instanceof Programare)
                .map(entity -> (Programare) entity)
                .toList();
    }


}
