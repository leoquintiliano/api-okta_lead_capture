package br.com.leadersofts.leadcapture.user.record;

import br.com.leadersofts.leadcapture.user.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
