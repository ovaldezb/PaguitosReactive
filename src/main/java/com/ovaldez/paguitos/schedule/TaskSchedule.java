package com.ovaldez.paguitos.schedule;

import com.ovaldez.paguitos.service.CreditoService;
import com.ovaldez.paguitos.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableScheduling
public class TaskSchedule {

    @Autowired
    private CreditoService creditoService;
    @Autowired
    private EmailServiceImpl emailService;

    //@Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 6 * * ?")
    public void scheduledFix(){
        int DAY_OF_MONTH_14 = 14;
        int DAY_1_BEFORE_END_MONTH=2;
        String datePattern = "dd-MM-yyyy";
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.of(today.getYear(),today.getMonth(),1,6,30);
        LocalDateTime nextMonth = now.plusMonths(1);
        long diffInDays = ChronoUnit.DAYS.between(today,nextMonth);
        System.out.println("Dif in days "+diffInDays);
        String fechaPago = "";
        LocalDateTime finMes = nextMonth.minusDays(1);
        if(today.getDayOfMonth()==DAY_OF_MONTH_14){
            fechaPago = DateTimeFormatter.ofPattern(datePattern).format(today.plusDays(1));
        }else if(diffInDays==DAY_1_BEFORE_END_MONTH){
            switch (finMes.getDayOfMonth()){
                case 28: fechaPago = DateTimeFormatter.ofPattern(datePattern).format(today.plusDays(1));
                case 29: fechaPago = DateTimeFormatter.ofPattern(datePattern).format(today.plusDays(1));
                case 30: fechaPago = DateTimeFormatter.ofPattern(datePattern).format(today.plusDays(1));
                case 31: fechaPago = DateTimeFormatter.ofPattern(datePattern).format(today);
                break;
            }
        }
        final String fechaPagoFinal = fechaPago;
        if(today.getDayOfMonth()==DAY_OF_MONTH_14 || diffInDays==DAY_1_BEFORE_END_MONTH) {
            creditoService.getCreditoByStatus(false)
                    .doOnNext(credito -> {
                        emailService.sendEmailByPago(credito,fechaPagoFinal);
                    })
                    .doOnError(System.out::println)
                    .onErrorResume(RuntimeException.class,e->{
                        System.out.println("Error: "+e.getMessage());
                        return Mono.empty();
                    })
                    .subscribe();
        }
    }
}
