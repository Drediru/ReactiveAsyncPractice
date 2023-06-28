package com.example.demo;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        var result = list.stream().filter(player -> player.getAge() > 35)
                .collect(Collectors.groupingBy(Player::getClub));

        System.out.println(result);
    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){

    }


    @Test
    void clubsAgrupadosPorNacionalidad(){

    }

    @Test
    void clubConElMejorJugador(){

    }

    @Test
    void ElMejorJugador(){

    }

    @Test
    void mejorJugadorSegunNacionalidad(){

    }


}
