package com.nelioalves.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Disciplina;
import com.nelioalves.cursomc.services.DisciplinaService;

@RestController
@RequestMapping(value = "/api/time/forecast")
public class MainResource {

        @Autowired
    private DisciplinaService service;
    
    @GetMapping("/all")
    public ResponseEntity<List<Disciplina>> findAll() {
        List<Disciplina> Disciplina = service.findAll();
        return ResponseEntity.ok().body(Disciplina);
    }

    
}
