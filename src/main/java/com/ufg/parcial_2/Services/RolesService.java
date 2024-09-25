package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Roles;

import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public Roles findById(Integer id) {
        return rolesRepository.findById(id).orElse(null);
    }
}