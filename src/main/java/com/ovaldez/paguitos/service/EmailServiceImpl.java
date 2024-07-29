package com.ovaldez.paguitos.service;

import com.ovaldez.paguitos.dto.Credito;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DecimalFormat;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CreditoService creditoService;

    @Value("${email.tdm.sender}")
    private String tdmEmailSender;

    @Value("${email.tdm.cc.marco}")
    private String emailTdmCC;

    public void sendEmailByCreditoId(String idCredito){
        creditoService.getCreditoById(idCredito)
                .doOnNext((credito)-> sendEmail(credito,"/templates/body-credito.vm","Hola "+credito.getCliente().getNombre()+" gracias por tu compra!",""))
                .subscribe();
    }

    public void sendEmailByPago(Credito credito, String fechaPago){
         sendEmail(credito,"/templates/body-pago.vm","Hola "+credito.getCliente().getNombre()+". Recordatorio de Pago", fechaPago);
    }

    private void sendEmail(Credito credito, String template, String subject, String fechaPago){
        DecimalFormat format = new DecimalFormat("$###,###,###.00");
        MimeMessage message = mailSender.createMimeMessage();
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS,"classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        Template templateCredito = velocityEngine.getTemplate(template);
        VelocityContext context = new VelocityContext();
        context.put("nombre",credito.getCliente().getNombre());
        context.put("marca",credito.getEquipo().getMarca());
        context.put("modelo",credito.getEquipo().getModelo());
        context.put("noSerie",credito.getEquipo().getNoSerie());
        context.put("costo",format.format(credito.getEquipo().getCosto()));
        context.put("enganche",format.format(credito.getEnganche()));
        context.put("noPagosTotales",credito.getNoPagosTotales());
        context.put("frecuencia",credito.getPlazoPago());
        context.put("pago",format.format(credito.getPago()));
        context.put("adeudo", format.format(credito.getAdeudo()));
        context.put("pagPend",credito.getNoPagosTotales()-credito.getPagos().size());
        context.put("fechaPago",fechaPago);
        StringWriter writer = new StringWriter();
        templateCredito.merge(context,writer);
        try {
            message.setFrom(new InternetAddress(tdmEmailSender));
            message.setRecipients(MimeMessage.RecipientType.TO,credito.getCliente().getCorreoElectronico());
            message.setRecipients(MimeMessage.RecipientType.CC,emailTdmCC);
            message.setSubject(subject);
            message.setContent(writer.toString(),"text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }
}
