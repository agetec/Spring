package com.estados.br.controler;

import com.estados.br.service.EstadosService;
import com.estados.br.service.PageableEst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estados.br.model.Estados;

import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/estados")
public class EstadosControler {
    @Autowired
    EstadosService estadosService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody Estados estado) {
        try {
            estadosService.salvarEstado(estado);
            return new ResponseEntity<>("Estado cadastrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao tentar cadastrar estado!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping (value = "/{id}")
    public ResponseEntity excluir(@PathVariable Integer id) {
        try {
            Optional<Estados> estados = estadosService.buscaByID(id);
            estadosService.excluir(estados.get());
            return new ResponseEntity<>("Estado excluído com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao tentar excluir estado!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estados> buscarPorId(@PathVariable Integer id) {
        Optional<Estados> estados = estadosService.buscaByID(id);
        return new ResponseEntity<Estados>(estados.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/{nome}")
    public ResponseEntity<Collection<Estados>> buscarByNome(@PathVariable String nome) {
        Collection<Estados> estadosCollecrtion = estadosService.buscarByNome(nome.toUpperCase());
        return new ResponseEntity<Collection<Estados>>(estadosCollecrtion, HttpStatus.OK);
    }

    @GetMapping
	public ResponseEntity<Page<Estados>> buscarPorPaginacao(PageableEst pageable) {
        Estados estados=new Estados();
        Example<Estados> est = Example.of(estados) ;
        if(!pageable.getNome().equals("undefined")&&!pageable.getNome().trim().equals("")) {
            est.getProbe().setNome(pageable.getNome());
        }
        return new ResponseEntity<Page<Estados>>(estadosService.buscarTodos(est, pageable), HttpStatus.OK);
    }

}
