package bank.sim.contocorrente.infrastructure.persistence.entities;

import java.time.LocalDateTime;
import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MongoEntity(collection="conto-corrente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ContoCorrenteEntity extends PanacheMongoEntityBase {

    @BsonId
    private String id;
    private String numeroConto;
    private String iban;
    private String bic;
    private String abi;
    private String cab;
    private double saldo = 0;
    private LocalDateTime dataApertura;
    private LocalDateTime dataChiusura;
    private int sogliaBonificoGiornaliera;
    private int sogliaBonificoMensile;
    private Set<String> clientiAssociati;
    
}