package com.allane.mobility.carRentalApp.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmRepositoryImpl implements EmRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
