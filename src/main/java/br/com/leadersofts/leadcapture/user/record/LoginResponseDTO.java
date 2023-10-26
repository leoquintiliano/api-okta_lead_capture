package br.com.leadersofts.leadcapture.user.record;

import java.util.List;

public record LoginResponseDTO(String token, String username, List<String> authorities) {
}
