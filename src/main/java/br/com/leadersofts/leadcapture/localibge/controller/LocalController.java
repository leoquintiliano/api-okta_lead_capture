package br.com.leadersofts.leadcapture.localibge.controller;

import br.com.leadersofts.leadcapture.localibge.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/localizacao")
public class LocalController {

    @Autowired
    private LocalService localService;

    @GetMapping("/uf")
    public ResponseEntity getUFs() {
//        return new ResponseEntity<List<UF>>(this.localService.getAllUFS(), HttpStatus.OK);
        return ResponseEntity.ok(this.localService.getAllUFS());
    }

    @GetMapping("/municipios/{idUF}")
    public ResponseEntity getMunicipios(@PathVariable("idUF") Long idUF) {
        return ResponseEntity.ok(this.localService.getAllMunicipios(idUF));
    }

}
