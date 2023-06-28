package com.example.demo;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class DojoReactiveTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35() {
        List<Player> list = CsvUtilFile.getPlayers();
        Mono<List<Player>> mono = Mono.just(list);
        Flux<Player> observable = Flux.fromIterable(list);

        observable.filter(jugador -> jugador.getAge() > 35)
                .collectList()
                .map(jugadorList -> {
                    long size = jugadorList.size();
                    Player primer = jugadorList.get(0);
                    Player ultimo = jugadorList.get((int) (size - 1));
                    System.out.println("Size: " + size);
                    System.out.println("Primer jugador: \n"+ primer + "\nSegundo jugador\n" + ultimo);
                    return jugadorList;
                }).subscribe();
    }


@Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable.filter(player -> player.getAge() > 35)
                .groupBy(Player::getClub)
                .subscribe(group -> {
                    String key = group.key();
                    System.out.println("+++++++++++++++++++");
                    System.out.println(key);
                    group.map(player -> {
                        System.out.println(player);
                        return player;
                    }).subscribe();
                });

    }


    @Test
    void mejorJugadorConNacionalidadFrancia(){
        Flux<Player> observable = Flux.fromIterable(CsvUtilFile.getPlayers());
        observable.filter(player -> player.getNational().equals("France"))
                .collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(a -> a.getWinners() / a.getGames()))
                .get()).subscribe(player -> System.out.println(player));

    }

    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);
        observable
                .groupBy(Player::getNational)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(tempList -> {
                            List<String> clubs = new ArrayList<>();
                            tempList.forEach(element -> clubs.add(element.getClub()));
                            Map<String, List<String>> map = new HashMap<>();
                            map.put(groupedFlux.key(), clubs);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((key, value) -> {
                        System.out.println("\n");
                        System.out.println(key + ": ");
                        value.forEach(System.out::println);
                    });
                });
    }

    @Test
    void clubConElMejorJugador(){
        Flux<Player> observable = Flux.fromIterable(CsvUtilFile.getPlayers());
        observable.collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(a -> a.getWinners() / a.getGames()))
                        .get()).subscribe(player -> System.out.println(player.getClub()));
    }

    @Test
    void clubConElMejorJugador2() {
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable
                .reduce((p1, p2) -> ((p1.getWinners()/p1.getGames())>=(p2.getWinners()/p2.getGames())?p1:p2))
                .subscribe(player -> System.out.println(player.getClub()));
    }

    @Test
    void ElMejorJugador() {
        Flux<Player> observable = Flux.fromIterable(CsvUtilFile.getPlayers());
        observable.collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(a -> a.getWinners() / a.getGames()))
                        .get()).subscribe(player -> System.out.println(player));
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable
                .groupBy(Player::getNational)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> {
                            Player best = list.stream().reduce((p1, p2)->((p1.getWinners()/p1.getGames())>=(p2.getWinners()/p2.getGames())?p1:p2)).get();
                            Map<String, Player> map = new HashMap<>();
                            map.put(groupedFlux.key(), best);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((k, v) -> {
                        System.out.println("\n");
                        System.out.println(k + ": ");
                        System.out.println(v);
                    });
                });
    }



}
