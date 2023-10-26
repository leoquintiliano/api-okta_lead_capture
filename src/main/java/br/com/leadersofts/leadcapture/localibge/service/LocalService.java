package br.com.leadersofts.leadcapture.localibge.service;

import br.com.leadersofts.leadcapture.client.LocalidadesIBGEClient;
import br.com.leadersofts.leadcapture.pojo.Municipio;
import br.com.leadersofts.leadcapture.pojo.UF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {

    @Autowired
    private LocalidadesIBGEClient client;

    public List<UF> getAllUFS() {
        return this.client.getUF();
    }

    public List<Municipio> getAllMunicipios(Long idUF) {
        return this.client.getMunicipios(idUF);
    }

}
