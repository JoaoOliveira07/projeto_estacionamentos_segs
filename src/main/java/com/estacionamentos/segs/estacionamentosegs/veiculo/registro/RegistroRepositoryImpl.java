package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RegistroRepositoryImpl implements RegistroRepository{
    EntityManager entityManager;
    RegistroRepositoryJpa repoJpa;

    @Override
    public List<Registro> findAll() {
        return repoJpa.findAll();
    }

    @Override
    public Registro encontrarRegistrosAtivosPorPlaca(String placa) {
        return repoJpa.encontrarRegistrosAtivosPorPlaca(placa);
    }

    @Override
    public List<RegistroVeiculoProjection> findAllByFilter(String filter, String orderBy) {

        String queryString =
                """
                    SELECT r AS registro, v AS veiculo, case when r.saida is null then 'Em andamento' else 'Concluído' end as status FROM Registro r
                    INNER JOIN Veiculo v on v.id = r.veiculos.id
                    WHERE concat(v.placa, ' ', v.modelo, ' ', v.tipo, ' ') ILIKE :filter and v.deleteTimeStamp IS NULL
                """;
        queryString = switch (RegistroOrderByTypes.fromString(orderBy)){
            case ENTRADA_DESC -> queryString.concat(" ORDER BY r.entrada DESC");
            case MODELO_DESC -> queryString.concat(" ORDER BY v.modelo DESC");
            case PLACA_DESC -> queryString.concat(" ORDER BY v.placa DESC");
            case SAIDA_DESC -> queryString.concat(" ORDER BY r.saida DESC");
            case STATUS_DESC -> queryString.concat(" ORDER BY status DESC");
            case TIPO_DESC -> queryString.concat(" ORDER By v.tipo DESC");
            case VALOR_DESC -> queryString.concat(" ORDER BY r.valor DESC");
        };

        Query query = entityManager.createQuery(queryString);
        query.setParameter("filter","%" + filter + "%");

        return query.getResultList()
                .stream().map(result -> {
                    Object[] resultArray = (Object[]) result;
                    return RegistroVeiculoProjection.builder()
                            .registro( (Registro) resultArray[0])
                            .veiculo((Veiculo) resultArray[1])
                            .status((String) resultArray[2])
                            .build();
                })
                .toList();

    }

    @Override
    public Registro save(Registro registro) {
        return repoJpa.save(registro);
    }
}
