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
@CrossOrigin(origins = "*")
public class EstadosControler {
    @Autowired
    EstadosService estadosService;

    @RequestMapping(method = RequestMethod.POST, value = "/salvar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity salvar(@RequestBody Estados estado) {
        try {
            estadosService.salvarEstado(estado);
            return new ResponseEntity<>("Estado cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao tentar cadastrar estado!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/excluir/{id}")
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

    @RequestMapping(method = RequestMethod.GET, value = "/buscarTodos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Estados>> buscarTodos() {
        Collection<Estados> estadosCollecrtion = estadosService.buscarTodos();
        return new ResponseEntity<Collection<Estados>>(estadosCollecrtion, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/buscarPorId/{id}")
    public ResponseEntity<Estados> buscarPorId(@PathVariable Integer id) {
        Optional<Estados> estados = estadosService.buscaByID(id);
        return new ResponseEntity<Estados>(estados.get(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/buscarByNome/{nome}")
    public ResponseEntity<Collection<Estados>> buscarByNome(@PathVariable String nome) {
        Collection<Estados> estadosCollecrtion = estadosService.buscarByNome(nome.toUpperCase());
        return new ResponseEntity<Collection<Estados>>(estadosCollecrtion, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/buscarPorPaginacao",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Estados>> buscarPorPaginacao(PageableEst pageable) {
        Estados estados=new Estados();
        Example<Estados> est = Example.of(estados) ;
        if(!pageable.getNome().equals("undefined")&&!pageable.getNome().trim().equals("")) {
            est.getProbe().setNome(pageable.getNome());
        }
        return new ResponseEntity<Page<Estados>>(estadosService.buscarTodos(est, pageable), HttpStatus.OK);
    }

}
