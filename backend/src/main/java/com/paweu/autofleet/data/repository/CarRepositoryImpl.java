package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository{
    private final DatabaseClient databaseClient;

    private static final String SELECT_QUERY = """
                SELECT c.car_id c_car_id, user_id c_user_id, brand c_brand, model c_model, car_year c_car_year, car_type c_car_type,
                	engine_type c_engine_type, c.engine c_engine, c.odometer c_odometer, number_plate c_number_plate,
                	c.last_update c_last_update, e.event_id e_event_id, e.car_id e_car_id, e.event_date e_event_date, e.last_update e_last_update,
                	e.odometer e_odometer, e.oil e_oil, e.oil_filter e_oil_filter, e.air_filter e_air_filter, e.timing_belt_kit e_timing_belt_kit,
                	e.description e_description
                	FROM public.car c
                	LEFT JOIN public.event e on c.car_id = e.car_id
            """;
    @Override
    public Flux<Car> findAllByUserId(UUID userId) {
        return databaseClient.sql(String.format("%s WHERE c.user_id = :id",SELECT_QUERY))
                .bind("id", userId)
                .fetch()
                .all()
                .bufferUntilChanged(res -> res.get("c_car_id"))
                .flatMap(Car::fromRows);
    }

    @Override
    public Mono<Car> findById(UUID carId) {
        return databaseClient.sql(String.format("%s WHERE c.car_id = :carId",SELECT_QUERY))
                .bind("carId", carId)
                .fetch()
                .all()
                .bufferUntilChanged(res -> res.get("c_car_id"))
                .flatMap(Car::fromRows)
                .singleOrEmpty();
    }

    @Override
    public Mono<Car> save(Car car) {
        return databaseClient.sql("""
                    INSERT INTO public.car(
                        user_id, brand, model, car_year, car_type, engine_type, engine, odometer, number_plate)
                        VALUES ( :userId, :brand, :model, :year, :carType, :engineType, :engine, :odometer, :numberPlate)
                """)
                    .filter(statement -> statement.returnGeneratedValues("car_id", "last_update"))
                    .bind("userId",car.getUserId())
                    .bind("brand", car.getBrand())
                    .bind("model", car.getModel())
                    .bind("year", car.getYear())
                    .bind("carType", car.getCarType())
                    .bind("engineType", car.getEngineType())
                    .bind("engine", car.getEngine())
                    .bind("odometer", car.getOdometer())
                    .bind("numberPlate", car.getNumberPlate())
                    .fetch().first()
                    .doOnNext(res -> {
                        car.setId((UUID) res.get("car_id"));
                        car.setLastUpdate((LocalDateTime) res.get("last_update"));
                    })
                    .thenReturn(car);
    }

    @Override
    public Mono<Long> update(Car car){
        return databaseClient.sql("""
                    UPDATE public.car
                        SET brand = :brand, model= :model, car_year= :year, car_type= :carType, engine_type= :engineType,
                        engine= :engine, odometer= :odometer, number_plate= :numberPlate, last_update= CURRENT_TIMESTAMP
                        WHERE car_id= :carId
                """)
                .bind("carId", car.getId())
                .bind("brand", car.getBrand())
                .bind("model", car.getModel())
                .bind("year", car.getYear())
                .bind("carType", car.getCarType())
                .bind("engineType", car.getEngineType())
                .bind("engine", car.getEngine())
                .bind("odometer", car.getOdometer())
                .bind("numberPlate", car.getNumberPlate())
                .fetch()
                .rowsUpdated()
                .flatMap(val -> {
                    if( val != 0)
                        return Mono.just(val);
                    else
                        return Mono.empty();
                });
    }

    @Override
    public Mono<Long> deleteById(UUID s) {
        return databaseClient.sql("""
                    DELETE FROM public.car WHERE car_id = :carId
                """)
                .bind("carId", s)
                .fetch()
                .rowsUpdated()
                .flatMap(res -> {
                    if (res != 0)
                        return Mono.just(res);
                    else
                        return Mono.empty();
                });
    }
}
