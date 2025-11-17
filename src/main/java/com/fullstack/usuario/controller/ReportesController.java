package com.fullstack.usuario.controller;

/*
 * NOTA: Este controlador está deshabilitado porque usa JdbcTemplate (SQL)
 * que no es compatible con MongoDB. Si se necesitan reportes en MongoDB,
 * se deben reescribir usando MongoTemplate o agregaciones de MongoDB.
 */

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/ventas-categoria")
    public List<Map<String, Object>> getVentasCategoria() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_ventas_categoria");
    }

    @GetMapping("/usuarios-region")
    public List<Map<String, Object>> getUsuariosPorRegion() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_usuarios_por_region");
    }

    @GetMapping("/clientes-activos")
    public List<Map<String, Object>> getClientesActivos() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_clientes_activos");
    }

    @GetMapping("/productos-vendidos")
    public List<Map<String, Object>> getProductosMasVendidos() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_productos_mas_vendidos");
    }

    @GetMapping("/stock-bajo")
    public List<Map<String, Object>> getStockBajo() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_stock_bajo");
    }

    @GetMapping("/pedidos-estado")
    public List<Map<String, Object>> getPedidosPorEstado() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_pedidos_por_estado");
    }

    @GetMapping("/auditoria")
    public List<Map<String, Object>> getAuditoriaProductos() {
        return jdbcTemplate.queryForList("SELECT * FROM vw_auditoria_productos");
    }
}
*/

