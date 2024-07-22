package com.ovaldez.paguitos.service;

import com.ovaldez.paguitos.dto.Cliente;
import com.ovaldez.paguitos.dto.Credito;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CreditoService creditoService;

    public void sendEmailByCreditoId(String idCredito){
        creditoService.getCreditoById(idCredito)
                .doOnNext(credito->{
                    System.out.println(credito.toString());
                    sendEmail(credito);
                })
                .subscribe();
    }

    /*public void sendEmail(String idCliente, Credito credito){
        clienteService.getClienteById(idCliente)
                .doOnNext((cliente)-> {
                    System.out.println(cliente.getCorreoElectronico());
                    MimeMessage message = mailSender.createMimeMessage();
                    try {
                        message.setFrom(new InternetAddress("reparaciones@tdm.mx"));
                        message.setRecipients(MimeMessage.RecipientType.TO,cliente.getCorreoElectronico());
                        message.setSubject("Hola "+cliente.getNombre()+" gracias por tu compra!");
                        message.setContent(this.getHtml(credito),"text/html; charset=utf-8");
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("reparaciones@tdm.mx");
                    message.setTo(cliente.getCorreoElectronico());

                    message.setSubject("Correo prueba1");
                    message.setText("Cuerpo del correo");

                    mailSender.send(message);
                })
                .subscribe();
    }*/

    private void sendEmail(Credito credito){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("reparaciones@tdm.mx"));
            message.setRecipients(MimeMessage.RecipientType.TO,credito.getCliente().getCorreoElectronico());
            message.setSubject("Hola "+credito.getCliente().getNombre()+" gracias por tu compra!");
            message.setContent(this.getHtml(credito),"text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }
    private String getHtml(Credito credito){
        DecimalFormat decimalFormat = new DecimalFormat("$###,###,###.00");
        return "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      .tableEquipo {\n" +
                "        margin-left: auto;\n" +
                "        margin-right: auto;\n" +
                "        border: 1px solid black;\n" +
                "        width: 30%;\n" +
                "        background-color: rgb(229, 238, 244);\n" +
                "      }\n" +
                "      .tablaCredito{\n" +
                "        margin-left: auto;\n" +
                "        margin-right: auto;\n" +
                "        border: 1px solid black;\n" +
                "        width: 25%;\n" +
                "        background-color: rgb(218, 242, 234);\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    Hola <strong>"+credito.getCliente().getNombre()+"</strong> gracias por comprar con nosotros, a\n" +
                "    continuación te presento un resumen de tu compra\n" +
                "    <p>Has adquirido el equipo:</p>\n" +
                "\n" +
                "    <table class=\"tableEquipo\">\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td style=\"border-bottom: 1px solid black; text-align: center;font-family: Verdana, Geneva, Tahoma, sans-serif;\">Marca:</td>\n" +
                "          <td style=\"border-bottom: 1px solid black; text-align: left;\">"+credito.getEquipo().getMarca()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td style=\"border-bottom: 1px solid black; text-align: center;font-family: Verdana, Geneva, Tahoma, sans-serif;\">Modelo:</td>\n" +
                "          <td style=\"border-bottom: 1px solid black; text-align: left;\">"+credito.getEquipo().getModelo()+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td style=\"text-align: center;font-family: Verdana, Geneva, Tahoma, sans-serif;\">No Serie:</td>\n" +
                "          <td style=\"text-align: left;\">"+credito.getEquipo().getNoSerie()+"</td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <p>Los datos de tu crédito son:</p>\n" +
                "    <table class=\"tablaCredito\">\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td style=\"border-bottom: 1px solid black;font-family: Verdana, Geneva, Tahoma, sans-serif;\">Costo:</td>\n" +
                "          <td style=\"border-bottom: 1px solid black;\">"+decimalFormat.format(credito.getEquipo().getCosto())+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td style=\"border-bottom: 1px solid black;font-family: Verdana, Geneva, Tahoma, sans-serif;\">Enganche:</td>\n" +
                "          <td style=\"border-bottom: 1px solid black\">"+decimalFormat.format(credito.getEnganche())+"</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "          <td style=\"font-family: Verdana, Geneva, Tahoma, sans-serif;\">Pagos:</td>\n" +
                "          <td>"+credito.getNoPagosTotales()+" pagos "+credito.getPlazoPago()+" de "+credito.getPago()+"</td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";
    }

}
