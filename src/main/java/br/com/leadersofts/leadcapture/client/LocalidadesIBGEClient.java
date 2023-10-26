package br.com.leadersofts.leadcapture.client;

import br.com.leadersofts.leadcapture.pojo.Municipio;
import br.com.leadersofts.leadcapture.pojo.UF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class LocalidadesIBGEClient {

    @Value("${ibge.api.url}")
    private String ibgeAPIURL;

    @Value("${ibge.api.scheme}")
    String scheme;

    @Value("${ibge.api.host}")
    String host;

    @Value("${ibge.api.path}")
    String path;

    RestTemplate restTemplate = new RestTemplate();

    public List<UF> getUF() {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host).
                path(this.path.concat("/estados").concat("/"))
                .build();
        ResponseEntity<List> entity = this.restTemplate.getForEntity(uri.toUriString(), List.class);

        return entity.getBody();
    }

    public List<Municipio> getMunicipios(Long idUF) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host).
                path(this.path.concat("/estados").concat("/").concat(idUF+"/").concat("municipios") )
                .build();
        ResponseEntity<List> entity = this.restTemplate.getForEntity(uri.toUriString(), List.class);

        return entity.getBody();
    }


}
