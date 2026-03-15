package com.example.confeitariaMacedoFarias.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppService {

    private final RestTemplate restTemplate;

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.api.token}")
    private String whatsappApiToken;

    @Value("${whatsapp.phone.number}")
    private String whatsappPhoneNumber;

    public void sendOrderMessage(String clientName, String phone, String address, String product, String value) {
        String message = String.format(
            "🛒 Novo pedido recebido\n\n" +
            "Cliente: %s\n" +
            "Telefone: %s\n" +
            "Endereço: %s\n" +
            "Produto: %s\n" +
            "Valor: R$ %s",
            clientName, phone, address, product, value
        );

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(whatsappApiToken);

            String requestBody = String.format(
                "{\"phone\": \"%s\", \"message\": \"%s\"}",
                whatsappPhoneNumber, message.replace("\n", "\\n")
            );

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(whatsappApiUrl, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Mensagem WhatsApp enviada com sucesso para o pedido");
            } else {
                log.error("Erro ao enviar mensagem WhatsApp: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem WhatsApp", e);
        }
    }
}