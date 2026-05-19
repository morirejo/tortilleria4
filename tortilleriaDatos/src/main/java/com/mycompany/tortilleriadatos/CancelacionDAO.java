/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriadatos;

import com.mongodb.client.MongoCollection;
import com.mycompany.tortilleriadtos.CancelacionDTO;
import com.mycompany.tortilleriadtos.DetalleVentaDTO;
import com.mycompany.tortilleriadtos.VentaDTO;
import com.mycompany.tortilleriadtos.VentaLocalDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author jorge
 */
public class CancelacionDAO implements ICancelacionDAO {
    private final MongoCollection<Document> collection;

    public CancelacionDAO() {
        this.collection = ConexionDB.getInstance()
                .getDatabase()
                .getCollection("cancelaciones");
        System.out.println("Documentos en cancelaciones: " + collection.countDocuments());
    }

    public boolean registrarCancelacion(CancelacionDTO cancelacion) {
        try {
            Document doc = new Document()
                    .append("idVenta", cancelacion.getIdVenta())
                    .append("fechaCancelacion", cancelacion.getFechaCancelacion())
                    .append("motivo", cancelacion.getMotivo() != null ? cancelacion.getMotivo() : "");

            if (cancelacion.getVentaOriginal() != null) {
                VentaDTO v = cancelacion.getVentaOriginal();
                List<Document> prods = new ArrayList<>();
                if (v.getCarrito() != null) {
                    for (DetalleVentaDTO d : v.getCarrito()) {
                        prods.add(new Document("nombreProducto", d.getNombreProducto())
                                .append("tipoProducto", d.getTipoProducto())
                                .append("cantidadKilos", d.getCantidadKilos())
                                .append("subtotal", d.getSubtotal()));
                    }
                }
                String metodoPago = "N/A";
                if (v instanceof VentaLocalDTO) {
                    metodoPago = ((VentaLocalDTO) v).getMetodoPago();
                }
                Document ventaDoc = new Document("idVenta", v.getIdVenta())
                        .append("montoTotal", v.getMontoTotal())
                        .append("fecha", v.getFechaHora())
                        .append("tipoVenta", v.getTipoVenta())
                        .append("metodoPago", metodoPago)
                        .append("productos", prods);
                doc.append("ventaOriginal", ventaDoc);
            }

            collection.insertOne(doc);
            cancelacion.setIdCancelacion(doc.getObjectId("_id").toHexString());
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar cancelación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CancelacionDTO> obtenerTodasLasCancelaciones() {
        System.out.println("Colección: " + collection.getNamespace());
    System.out.println("Total docs: " + collection.countDocuments());
        System.out.println("Buscando en colección: " + collection.getNamespace());
    List<CancelacionDTO> lista = new ArrayList<>();
    for (Document doc : collection.find()) {
        System.out.println("Doc encontrado: " + doc.toJson());
        lista.add(mapearDocumentoACancelacion(doc));
    }
    System.out.println("Total mapeados: " + lista.size());
    return lista;
    }

    private CancelacionDTO mapearDocumentoACancelacion(Document doc) {
        CancelacionDTO c = new CancelacionDTO();
        ObjectId oid = doc.getObjectId("_id");
        if (oid != null) c.setIdCancelacion(oid.toHexString());
        c.setIdVenta(doc.getInteger("idVenta", 0));
        c.setFechaCancelacion(doc.getDate("fechaCancelacion"));
        c.setMotivo(doc.getString("motivo"));
        c.setUsuario(doc.getString("usuario") != null ? doc.getString("usuario") : "Desconocido"); // NUEVO

        Document ventaDoc = (Document) doc.get("ventaOriginal");
        if (ventaDoc != null) {
            List<DetalleVentaDTO> productos = new ArrayList<>();
            List<Document> prods = (List<Document>) ventaDoc.get("productos");
            if (prods != null) {
                for (Document p : prods) {
                    Number kilos = (Number) p.get("cantidadKilos");
                    Number subtotal = (Number) p.get("subtotal");
                    productos.add(new DetalleVentaDTO(
                            p.getString("nombreProducto"),
                            p.getString("tipoProducto") != null ? p.getString("tipoProducto") : "",
                            kilos != null ? kilos.doubleValue() : 0,
                            subtotal != null ? subtotal.doubleValue() : 0));
                }
            }
            Number total = (Number) ventaDoc.get("montoTotal");
            String metodoPago = ventaDoc.getString("metodoPago");
            String vUsuario = ventaDoc.getString("usuario") != null ? ventaDoc.getString("usuario") : "Desconocido"; // NUEVO
            VentaLocalDTO venta = new VentaLocalDTO(
                    ventaDoc.getInteger("idVenta", 0),
                    total != null ? total.doubleValue() : 0,
                    ventaDoc.getDate("fecha") != null ? ventaDoc.getDate("fecha") : new Date(),
                    metodoPago != null ? metodoPago : "N/A",
                    vUsuario,
                    productos);
            c.setVentaOriginal(venta);
        }
        return c;
    }

}
