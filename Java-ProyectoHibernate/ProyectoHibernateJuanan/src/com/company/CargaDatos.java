package com.company;

import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.mapping.List;
import org.hibernate.query.Query;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class CargaDatos {

    static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    // **************************************************************** PROVEEDORES ***************************************************************

    public static ArrayList<Proveedores> cargarProveedores() {

        ArrayList<Proveedores> listaProveedores = new ArrayList<>();
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        // Borrar un departamento eligiendo ID
        // DepartamentosEntity dep = (DepartamentosEntity) session.load(DepartamentosEntity.class, 10);
        // Borrar Empleado eligiendo ID
        // Empleados2Entity em = (Empleados2Entity) session.load(Empleados2Entity.class, 5);


        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROVEEDORES ACTUALIZADA:");
        System.out.format("%-5s%-40s%-30s%-20s%n", "ID", "PROVEEDOR", "RESPONSABLE", "DIRECCION");


        // Recuperar lista --> No hace falta poner el selec y los campos a mostrar, ya se especifica en el sout más abajo
        Query lista = session.createQuery("from Proveedores");


        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {

                Proveedores proveedor = (Proveedores) iter.next();
                System.out.format("%-5d%-40s%-30s%-20s%n", proveedor.getIdProveedor(), proveedor.getProveedor(), proveedor.getResponsableVentas(), proveedor.getDirCp());

                listaProveedores.add(proveedor);
            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");
        session.close();

        return listaProveedores;


    }


    public static void insercionProveedor(String proveedor, String responsableVentas, String direccion) {

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // Instancio un proveedor nuevo
        Proveedores prov = new Proveedores();

        prov.setProveedor(proveedor);
        prov.setResponsableVentas(responsableVentas);
        prov.setDirCp(direccion);


        try {

            session.save(prov);
            tx.commit();

            // Mensaje confirmación
            JOptionPane.showMessageDialog(null, "Se ha insertado correctamente el proveedor " + proveedor.toUpperCase());


        } catch (TransientPropertyValueException e) {
            System.out.println("EL PROVEEDOR NO EXISTE");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("Propiedad:%s%n", e.getPropertyName());
        } catch (PersistentObjectException p) {
            //p.getMessage();
        } catch (ConstraintViolationException e) {
            System.out.println("PROVEEDOR DUPLICADO");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("COD ERROR:%d%n", e.getErrorCode());
            System.out.printf("ERROR SQL:%s%n", e.getSQLException().getMessage());
        }

        session.close();

    }

    public static void modificarProveedor(int IDmodificarProveedor, String nuevoNombre, String nuevoApellido, String nuevaDireccion) {
        SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        Proveedores prov = new Proveedores();

        try {
            // Cargo el proveedor a modificar, eligiendo su ID
            prov = (Proveedores) session.load(Proveedores.class, IDmodificarProveedor);

            if (prov == null) {
                System.out.println("El proveedor no existe");

            } else {
                // Actualizo los datos que me interesen
                prov.setIdProveedor(IDmodificarProveedor);
                prov.setProveedor(nuevoNombre);
                prov.setResponsableVentas(nuevoApellido);
                prov.setDirCp(nuevaDireccion);

                // Actualiza, modifica el objeto
                session.update(prov);
                // Actualizo la base de datos
                tx.commit();

                JOptionPane.showMessageDialog(null, "Se han modificado correctamente el proveedor " + nuevoNombre.toUpperCase());

            }
        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE EL PROVEEDOR");
            JOptionPane.showMessageDialog(null, "No existe el proveedor " + nuevoNombre.toUpperCase(), "Error", JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
    }

    public static void borrarProveedor(int idProveedor) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // PROVEEDOR A BORRAR
        Proveedores prov = (Proveedores) session.load(Proveedores.class, idProveedor);
        try {

            if (prov != null) {

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {
                    session.delete(prov);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el proveedor " + prov.getProveedor().toUpperCase());
                }


            }


        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE EL PROVEEDOR");
        } catch (ConstraintViolationException c) {
            System.out.println("NO SE PUEDE ELIMINAR");
        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
        session.close();
    }


    public static ArrayList<Proveedores> consultaCodigoProveedor(String codigo) {

        // int idCodigo = Integer.parseInt(codigo); // No hace falta convertor el cdatop de la caja a int, para hacer la consulta, con el String vale

        ArrayList<Proveedores> listaProveedores = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROVEEDORES QUE QUE INCLUYEN EN SU CÓDIGO: " + codigo);
        System.out.format("%-5s%-30s%-30s%-30s%n", "ID", "PROVEEDOR", "RESPONSABLE VENTAS", "DIRECION");


        Query lista = session.createQuery("from Proveedores where id_proveedor like '%" + codigo + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proveedores proveedor = (Proveedores) iter.next();
                System.out.format("%-5d%-30s%-30s%-30s%n", proveedor.getIdProveedor(), proveedor.getProveedor(), proveedor.getResponsableVentas(), proveedor.getDirCp());

                listaProveedores.add(proveedor);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");
        session.close();

        return listaProveedores;

    }


    public static ArrayList<Proveedores> consultaNombreProveedor(String nombreProveedor) {


        ArrayList<Proveedores> listaProveedores = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROVEEDORES QUE QUE INCLUYEN EN SU NOMBRE: " + nombreProveedor);
        System.out.format("%-5s%-30s%-30s%-30s%n", "ID", "PROVEEDOR", "RESPONSABLE VENTAS", "DIRECION");


        Query lista = session.createQuery("from Proveedores where proveedor  like '%" + nombreProveedor + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proveedores proveedor = (Proveedores) iter.next();
                System.out.format("%-5d%-30s%-30s%-30s%n", proveedor.getIdProveedor(), proveedor.getProveedor(), proveedor.getResponsableVentas(), proveedor.getDirCp());

                listaProveedores.add(proveedor);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaProveedores;

    }


    public static ArrayList<Proveedores> consultaDireccionProveedor(String direccionProveedor) {


        ArrayList<Proveedores> listaProveedores = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROVEEDORES QUE QUE INCLUYEN EN SU DIRECCION: " + direccionProveedor);
        System.out.format("%-5s%-30s%-30s%-30s%n", "ID", "PROVEEDOR", "RESPONSABLE VENTAS", "DIRECION");


        Query lista = session.createQuery("from Proveedores where dirCp  like '%" + direccionProveedor + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proveedores proveedor = (Proveedores) iter.next();
                System.out.format("%-5d%-30s%-30s%-30s%n", proveedor.getIdProveedor(), proveedor.getProveedor(), proveedor.getResponsableVentas(), proveedor.getDirCp());

                listaProveedores.add(proveedor);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaProveedores;

    }

    // **************************************************************** GESTIONES ***************************************************************

    public static ArrayList<Gestion> cargarGestiones() {

        ArrayList<Gestion> listaGestion = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();


        System.out.println("\n=================================================================================================================================================================================");
        System.out.println("LISTA DE GESTIONES ACTUALIZADA:");
        System.out.format("%-15s%-15s%-40s%-15s%-40s%-10s%-30s%-20s%n", "ID GESTION", "ID PROYECTO", "PROYECTO", "ID PROV", "PROVEEDOR", "ID PIEZA", "PIEZA", "CANTIDAD");


        // Recuperar lista --> Select con los campos especificados ( no hace falta si tengo las foreign key como objeto en la clase Gestion
        // Query lista = session.createQuery("select a.idGestion, b.proyecto, c.proveedor, d.pieza, a.cantidad from Gestion a, Proyectos b, Proveedores c, Piezas d where a.proyectoByIdProyecto.idProyecto=b.idProyecto and a.proveedorByIdProveedor.idProveedor=c.idProveedor and a.piezaByIdPieza.idPieza=d.idPieza");


        // No hace falta hacer una consulta de tablas cruzadas, como tengo objetos Proyectos, Proveedores y Piezas referenciadas (como Foreign keys) puedo acceder a todos sus atributos
        Query lista = session.createQuery("from Gestion order by ID_gestion");

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Gestion gestion = (Gestion) iter.next();
                //System.out.format("%-5d%-10d%-10d%-10d%.2f%n", gestion.getIdGestion(), gestion.getIdGestionProyecto(), gestion.getIdGestionProveedor(), gestion.getIdGestionPieza(), gestion.getCantidad());
                System.out.format("%-15d%-15d%-40s%-15d%-40s%-10d%-30s%-20.2f%n", gestion.getIdGestion(), gestion.getProyectoByIdProyecto().getIdProyecto(), gestion.getProyectoByIdProyecto().getProyecto(), gestion.getProveedorByIdProveedor().getIdProveedor(), gestion.getProveedorByIdProveedor().getProveedor(), gestion.getPiezaByIdPieza().getIdPieza(), gestion.getPiezaByIdPieza().getPieza(), gestion.getCantidad());

                listaGestion.add(gestion);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n=================================================================================================================================================================================");

        session.close();

        return listaGestion;


    }


    public static void insercionGestion(Piezas pieza, Proveedores proveedor, Proyectos proyecto, Double cantidad) {

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // Instancio un proveedor nuevo
        Gestion gestion = new Gestion();

        gestion.setPiezaByIdPieza(pieza);
        gestion.setProveedorByIdProveedor(proveedor);
        gestion.setProyectoByIdProyecto(proyecto);
        gestion.setCantidad(cantidad);


        try {

            session.save(gestion);
            tx.commit();

            // Mensaje confirmación
            JOptionPane.showMessageDialog(null, "Se ha insertado correctamente la gestion ");


        } catch (TransientPropertyValueException e) {
            System.out.println("LA GESTIÓN NO EXISTE");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("Propiedad:%s%n", e.getPropertyName());
            JOptionPane.showMessageDialog(null, "ERROR:\n" + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);

        } catch (PersistentObjectException p) {
            p.getMessage();
            JOptionPane.showMessageDialog(null, "ERROR:\n" + p.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);

        } catch (ConstraintViolationException e) {
            System.out.println("GESTIÓN DUPLICADO");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("COD ERROR:%d%n", e.getErrorCode());
            System.out.printf("ERROR SQL:%s%n", e.getSQLException().getMessage());
            JOptionPane.showMessageDialog(null, "ERROR:\n" + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

        session.close();

    }

    public static void modificarCantidadGestion(int idGestion, Double cantidad) {

        SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        Gestion gestion = new Gestion();

        try {
            // Cargo el proveedor a modificar, eligiendo su ID
            gestion = (Gestion) session.load(Gestion.class, idGestion);

            if (gestion == null) {
                System.out.println("La gestión no existe");

            } else {
                // Actualizo los datos que me interesen
                gestion.setCantidad(cantidad);

                // Actualiza, modifica el objeto
                session.update(gestion);
                // Actualizo la base de datos
                tx.commit();

                JOptionPane.showMessageDialog(null, "Se han modificado correctamente con la cantidad " + cantidad + "  de la gestion " + idGestion);

            }
        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE LA GESTIÓN");
            JOptionPane.showMessageDialog(null, "No existel a gestión id: " + idGestion, "Error", JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
    }


    public static void borrarGestion(int idGestion) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // GESTION A BORRAR
        Gestion gestion = (Gestion) session.load(Gestion.class, idGestion);
        try {

            if (gestion != null) {

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {
                    session.delete(gestion);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el proveedor " + idGestion);
                }


            }


        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE LA GESTIÓN");
        } catch (ConstraintViolationException c) {
            System.out.println("NO SE PUEDE ELIMINAR");
        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
        session.close();
    }


    // **************************************************************** CONSULTAS SUMINISTROS POR PROVEEDOR  ***************************************************************

    // Suministros por poveedor
    public static int consultaPiezasProveedorGestion(int idProveedor) {


        int proyectosProveedor = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        Query lista = session.createQuery(" select count(*) from Gestion where ID_gestion_proveedor=" + idProveedor);

        if (lista.uniqueResult() != null) {
            proyectosProveedor = Integer.parseInt(lista.uniqueResult().toString());
        }

        System.out.println("Piezas suministradas por el Proveedor " + idProveedor + ": " + proyectosProveedor);

        session.close();

        return proyectosProveedor;

    }

    // Suministros por proveedor
    public static int numeroProyectosProveedorGestion(int idProveedor) {

        int proyectosProveedor = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        // Tengo que hacer la consulta como 'createSQLQuery' si no me da problemas como 'createQuery'
        Query lista = session.createSQLQuery("select count(*) from (select ID_gestion_proveedor from Gestion  where ID_gestion_proveedor=" + idProveedor + " group by ID_gestion_proyecto) as gc");

        if (lista.uniqueResult() != null) {
            proyectosProveedor = Integer.parseInt(lista.uniqueResult().toString());
        }

        System.out.println("Numero proyectos del proveedor " + idProveedor + ": " + proyectosProveedor);
        System.out.println();

        // De otra forma, si solo sabes que vas a obtener un solo resultado
        //proyectosProveedor = String.valueOf(lista.list().get(0));
        //System.out.println("Cantidad Proyectos ProveedorLista.list: " + proyectosProveedor);


        session.close();

        return proyectosProveedor;

    }

    // Suministros por proveedor
    public static String consultaProyectosProveedorGestion(int idProveedor) {

        //NO ME SALE EL NUMERO DE VECES QUE TIENE PROYECTOS DIFERENTES; TENGO QUE IMPROVISAR CON UN CONTADOR, PARA LLEVAR LA CUENTA

        String proyectosProveedor = "";
        int contador = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Query lista = session.createQuery("select count(*) from Gestion  where ID_gestion_proveedor=" + idProveedor + " group by ID_gestion_proyecto");


        //System.out.println("Cantidad Proyectos Proveedor: " + (long) lista.list().get(0));

        // Obtenemos un Iterador y recorremos la lista

        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                contador++;

                // Extraer el objeto
                Object[] par = (Object[]) iter.next();

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }


        session.close();

        proyectosProveedor = String.valueOf(contador);
        System.out.println("Número proyectos: " + proyectosProveedor);

        return proyectosProveedor;

    }

    // Suministros por proveedor

    public static ArrayList<Gestion> cargarPiezasProyectosGestion(int idProveedor) {

        ArrayList<Gestion> listaGestion = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        //private final String[] columnas = {"ID GESTIÓN","PROVEEDOR", "PROYECTO", "PIEZA"};

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA ACTUALIZADA DE PROYECTOS DEL PROVEEDOR " + idProveedor + ": ");
        System.out.format("%-15s%-30s%-30s%-30s%-30s%n", "ID GESTIÓN", "PROYECTO", "PROVEEDOR", "PIEZA", "CANTIDAD");


        // Recuperar lista --> Select con los campos especificados ( no hace falta si tengo las foreign key como objeto en la clase Gestion
        // Query lista = session.createQuery("select a.idGestion, b.proyecto, c.proveedor, d.pieza, a.cantidad from Gestion a, Proyectos b, Proveedores c, Piezas d where a.proyectoByIdProyecto.idProyecto=b.idProyecto and a.proveedorByIdProveedor.idProveedor=c.idProveedor and a.piezaByIdPieza.idPieza=d.idPieza");


        // No hace falta hacer una consulta de tablas cruzadas, como tengo objetos Proyectos, Proveedores y Piezas referenciadas (como Foreign keys) puedo acceder a todos sus atributos
        Query lista = session.createQuery("from Gestion where ID_gestion_proveedor=" + idProveedor);

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Gestion gestion = (Gestion) iter.next();

                System.out.format("%-15d%-30s%-30s%-30s%.2f%n", gestion.getIdGestion(), gestion.getProyectoByIdProyecto().getProyecto(), gestion.getProveedorByIdProveedor().getProveedor(), gestion.getPiezaByIdPieza().getPieza(), gestion.getCantidad());

                listaGestion.add(gestion);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaGestion;


    }


    // **************************************************************** CONSULTAS PIEZAS SUMINISTRADAS A PROYECTOS  ***************************************************************


    // Piezas suministradas a proyectos
    public static int consultaProveedoresSuministranPiezaGestion(int idPieza) {

        int numeroProveedores = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  count(distinct(ID_gestion_proveedor))  from gestion where ID_gestion_pieza=" + idPieza);

        if (lista.uniqueResult() != null) {
            numeroProveedores = Integer.parseInt(lista.uniqueResult().toString());
        }

        System.out.println("Número proveedores que suministran la pieza " + idPieza + ": " + numeroProveedores);


        // NúmeroProveedores= String.valueOf(lista.list().get(0)); // Otra forma de coger un solo resultado


        session.close();

        return numeroProveedores;

    }

    // Piezas suministradas a proyectos
    public static double consultaPiezasSuministradasGestion(int idPieza) {

        double cantidadPiezas = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Query lista = session.createQuery(" select sum(ges.cantidad) from Gestion as ges where ID_gestion_pieza=" + idPieza);

        if (lista.uniqueResult() != null) {
            cantidadPiezas = (Double) lista.uniqueResult();
        }

        System.out.println("Cantidad total suministrada de la pieza " + idPieza + ": " + cantidadPiezas);
        System.out.println();


/*        // Otra forma de obtener un único resultado:
        try {
            Object numeroPiezas = lista.getSingleResult();

            if (numeroPiezas != null) {
                System.out.println("Numero piezas -> " + numeroPiezas);
                System.out.println("Cantidad Piezas Proveedor3: " + lista.list().get(0));
                cantidadPiezas = (Double) lista.uniqueResult();
                System.out.println("Resultado: " + cantidadPiezas);

            }
        } catch (ClassCastException e) {
            //e.printStackTrace();
        }*/


        session.close();

        return cantidadPiezas;

    }


    // Piezas suministradas a proyectos
    public static int consultaProyectosPiezaGestion(int idPieza) {


        int proyectosPieza = 0;


        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        //Query lista = session.createSQLQuery("select count(ID_gestion_proveedor) from Gestion  where ID_gestion_pieza=" + idPieza);
        Query lista = session.createSQLQuery("select count(*) from( select count(ID_gestion_pieza) from gestion where ID_gestion_pieza="+idPieza+" group by ID_gestion_proyecto) as g");

        if (lista.uniqueResult() != null) {
            proyectosPieza = Integer.parseInt(lista.uniqueResult().toString());
        }
        System.out.println("Número proyectos de la pieza " + idPieza + ": " + proyectosPieza);

        /*
        // Otra forma, pero con el contador y haciendo la query de tipo 'createQuery', porque sino no devulve un número si no itera filas
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                proyectosPieza++;


                // Extraer el objeto
                Object[] par = (Object[]) iter.next();

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }
        */


        session.close();

        return proyectosPieza;

    }


    // Suministros por proveedor
    public static ArrayList<Gestion> cargarProyectosProveedoresGestion(int idPieza) {

        ArrayList<Gestion> listaGestion = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();


        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA ACTUALIZADA DE PROYECTOS DE lA PIEZA " + idPieza + ": ");
        System.out.format("%-15s%-30s%-30s%-30s%-30s%n", "ID GESTIÓN", "PROYECTO", "PROVEEDOR", "PIEZA", "CANTIDAD");


        // Recuperar lista --> Select con los campos especificados ( no hace falta si tengo las foreign key como objeto en la clase Gestion
        // Query lista = session.createQuery("select a.idGestion, b.proyecto, c.proveedor, d.pieza, a.cantidad from Gestion a, Proyectos b, Proveedores c, Piezas d where a.proyectoByIdProyecto.idProyecto=b.idProyecto and a.proveedorByIdProveedor.idProveedor=c.idProveedor and a.piezaByIdPieza.idPieza=d.idPieza");

        // No hace falta hacer una consulta de tablas cruzadas, como tengo objetos Proyectos, Proveedores y Piezas referenciadas (como Foreign keys) puedo acceder a todos sus atributos
        Query lista = session.createQuery("from Gestion where ID_gestion_pieza=" + idPieza);

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Gestion gestion = (Gestion) iter.next();

                System.out.format("%-15d%-30s%-30s%-30s%.2f%n", gestion.getIdGestion(), gestion.getProyectoByIdProyecto().getProyecto(), gestion.getProveedorByIdProveedor().getProveedor(), gestion.getPiezaByIdPieza().getPieza(), gestion.getCantidad());

                listaGestion.add(gestion);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaGestion;


    }


    // *******************************************************************************************************  ESTADÍSTICAS  *******************************************************************************************************

    public static ArrayList<Estadistica> piezasCantidadSuministradaProyectos() {

        ArrayList<Estadistica> listaEstadistica = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();


        System.out.println("\n=============================================================================================================================================================");
        System.out.println("LISTA ACTUALIZADA DE PIEZAS Y CANTIDAD SUMINISTRADAS POR PROYECTOS:");
        System.out.format("%-15s%-30s%-30s%-30s%-30s%-30s%n", "ID PROYECTO", "PROYECTO", "RESPONSABLE", "NÚMERO PIEZAS", "CANTIDAD SUMINISTRADA", "NÚMERO PROVEEDORES");

        Query lista = session.createSQLQuery("select  p.id_proyecto, p.proyecto, p.ciudad, count(g.ID_gestion_pieza) as 'Numero Piezas', sum(g.cantidad) as 'Cantidad suministrada', count(distinct g.ID_gestion_proveedor) as 'Numero Proveedores' from Gestion g, proyectos p where g.ID_gestion_proyecto=p.id_proyecto  group by g.ID_gestion_proyecto order by 'Numero Piezas' DESC");

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.stream().iterator();
        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                int id = Integer.parseInt(par[0].toString());
                String proyecto = String.valueOf(par[1]);
                String ciudad = String.valueOf(par[2]);
                int numPiezas = Integer.parseInt(par[3].toString());
                Double cantidad = Double.parseDouble(par[4].toString());
                int numProveedores = Integer.parseInt(par[5].toString());


                System.out.format("%-15d%-30s%-30s%-30d%-30.2f%-30d%n", id, proyecto, ciudad, numPiezas, cantidad, numProveedores);

                // Instancio un objeto de tipo Estadistica que coincide con los tipos de datos de las columnas de la consulta, para luego poder sacar el JTable
                Estadistica es = new Estadistica(id, proyecto, ciudad, numPiezas, cantidad, numProveedores);
                listaEstadistica.add(es);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n=============================================================================================================================================================");


        session.close();

        return listaEstadistica;


    }


    public static ArrayList<Estadistica> piezasCantidadSuministradaProveedores() {

        ArrayList<Estadistica> listaEstadistica = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();


        System.out.println("\n=============================================================================================================================================================");
        System.out.println("LISTA ACTUALIZADA DE PIEZAS Y CANTIDAD SUMINISTRADAS POR PROVEEDORES:");
        System.out.format("%-15s%-30s%-30s%-30s%-30s%-30s%n", "ID PROVEEDOR", "PROVEEDOR", "RESPONSABLE", "NÚMERO PROYECTOS", "CANTIDAD SUMINISTRADA", "NÚMERO PIEZAS");

        Query lista = session.createSQLQuery("select  p.id_proveedor, p.proveedor, p.responsableVentas, count(distinct g.ID_gestion_proyecto) as 'Numero Piezas', sum(g.cantidad) as 'Cantidad suministrada', count(g.ID_gestion_pieza) as 'Numero Proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor  group by g.ID_gestion_proveedor order by 'Numero Piezas' DESC");

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.stream().iterator();
        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                int id = Integer.parseInt(par[0].toString());
                String proveedor = String.valueOf(par[1]);
                String responsable = String.valueOf(par[2]);
                int numPiezas = Integer.parseInt(par[3].toString());
                Double cantidad = Double.parseDouble(par[4].toString());
                int numProveedores = Integer.parseInt(par[5].toString());


                System.out.format("%-15d%-30s%-30s%-30d%-30.2f%-30d%n", id, proveedor, responsable, numPiezas, cantidad, numProveedores);

                // Instancio un objeto de tipo Estadistica que coincide con los tipos de datos de las columnas de la consulta, para luego poder sacar el JTable
                Estadistica es = new Estadistica(id, proveedor, responsable, numPiezas, cantidad, numProveedores);
                listaEstadistica.add(es);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n=============================================================================================================================================================");


        session.close();

        return listaEstadistica;


    }


    public static String nombrePiezaMasCantidad() {

        String nombrePiezaMasCantidad = "";

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select z.pieza, sum(g.cantidad) as sum from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza  group by g.ID_gestion_pieza order by sum DESC Limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                nombrePiezaMasCantidad = String.valueOf(par[0]);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Pieza de la que se ha suministrado más cantidad: " + nombrePiezaMasCantidad);

        session.close();

        return nombrePiezaMasCantidad;

    }

    public static double piezaMasCantidad() {

        double piezaMasCantidad = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select z.pieza, sum(g.cantidad) as sum from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza  group by g.ID_gestion_pieza order by sum DESC Limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                piezaMasCantidad = Double.parseDouble(par[1].toString());

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Cantidad de la pieza que se ha suministrado más cantidad :" + piezaMasCantidad);

        session.close();

        return piezaMasCantidad;

    }

    public static String nombrePiezaMasProyectos() {

        String nombrePiezaMasProyectos = "";

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select distinct z.pieza, max(g.cantidad) from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by ID_gestion_proyecto, pieza order by max(cantidad) DESC LIMIT 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                nombrePiezaMasProyectos = String.valueOf(par[0]);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Nombre pieza que se ha suministrado a más proyectos: " + nombrePiezaMasProyectos);

        session.close();

        return nombrePiezaMasProyectos;

    }

    public static int cantidadPiezaProyectos() {

        int piezaMasCantidadProyectos = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        //Query lista = session.createSQLQuery("select  z.pieza, count(g.ID_gestion_proyecto) as proyectos from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by g.ID_gestion_proyecto, pieza order by proyectos DESC LIMIT 1");
        Query lista = session.createSQLQuery("select  z.pieza,max(g.cantidad),count(g.ID_gestion_proyecto) from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by ID_gestion_pieza  order by count(g.ID_gestion_proyecto) DESC LIMIT 1");
        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                piezaMasCantidadProyectos = Integer.parseInt(par[2].toString());

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Cantidad de piezas que se ha suministrado a más proyectos: "  + piezaMasCantidadProyectos);

        session.close();

        return piezaMasCantidadProyectos;

    }


    public static String nombreProveedorMasPiezas() {

        String nombreProveedor = "";

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor, sum(g.cantidad) from Gestion g, proveedores p where g.ID_gestion_proveedor= p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                nombreProveedor = String.valueOf(par[0]);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Proveedor que ha suministrado más cantidad de piezas: "  + nombreProveedor);


        session.close();

        return nombreProveedor;

    }

    public static double proveedorCantidadMasPiezas() {

        double piezaMasCantidadProyectos = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor, sum(g.cantidad) from Gestion g, proveedores p where g.ID_gestion_proveedor= p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                piezaMasCantidadProyectos = Double.parseDouble(par[1].toString());

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Cantidad de piezas del Proveedor que ha suministrado más cantidad de piezas: "  + piezaMasCantidadProyectos);


        session.close();

        return piezaMasCantidadProyectos;

    }

    public static String nombreProveedorMasProyectos() {

        String nombreProveedorMasProyectos = "";

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(distinct g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by count(distinct g.ID_gestion_proyecto) DESC limit 1");
        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                nombreProveedorMasProyectos = String.valueOf(par[0]);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Nombre del Proveedor que ha suministrado a más proyectos: "  + nombreProveedorMasProyectos);


        session.close();

        return nombreProveedorMasProyectos;

    }

    public static int proveedorCantidadMasProyectos() {

        int proveedorCantidadMasProyectos = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(distinct g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by count(distinct g.ID_gestion_proyecto) DESC limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                proveedorCantidadMasProyectos = Integer.parseInt(par[2].toString());


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Cantidad de piezas del Proveedor que ha suministrado a más proyectos: "  + proveedorCantidadMasProyectos);

        session.close();

        return proveedorCantidadMasProyectos;

    }


    public static String nombreProveedorMaspiezas() {

        String nombreProveedorMaspiezas = "";

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                nombreProveedorMaspiezas = String.valueOf(par[0]);


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Nombre del Proveedor que ha suministrado más piezas: "  + nombreProveedorMaspiezas);


        session.close();

        return nombreProveedorMaspiezas;

    }

    public static double proveedorCantidadMaspiezas() {

        double proveedorCantidadMaspiezas = 0;

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        // Hago la consulta como 'createSQLQuery' porque me la coge bien, como 'createQuery' no coge bien la sintaxis
        Query lista = session.createSQLQuery("select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1");

        Iterator iter = lista.stream().iterator();

        while (iter.hasNext()) {

            try {

                // EXTRAER OBJETOS DE LA CONSULTA

                Object[] par = (Object[]) iter.next();
                proveedorCantidadMaspiezas = Double.parseDouble(par[1].toString());


            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("Cantidad de piezas del Proveedor que ha suministrado más piezas: "  + proveedorCantidadMaspiezas);


        session.close();

        return proveedorCantidadMaspiezas;

    }


    // **************************************************************** PROYECTOS ***************************************************************

    public static ArrayList<Proyectos> cargarProyectos() {

        ArrayList<Proyectos> listaProyectos = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROYECTOS ACTUALIZADA:");
        System.out.format("%-5s%-40s%-30s%n", "ID", "PROYECTO", "CIUDAD");

        Query lista = session.createQuery("from Proyectos ");

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proyectos proyecto = (Proyectos) iter.next();
                System.out.format("%-5d%-40s%-30s%n", proyecto.getIdProyecto(), proyecto.getProyecto(), proyecto.getCiudad());

                listaProyectos.add(proyecto);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaProyectos;

    }


    public static void insercionProyecto(String nombreProyecto, String ciudad) {

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        // Instancio un proveedor nuevo
        Proyectos proyecto = new Proyectos();

        proyecto.setProyecto(nombreProyecto);
        proyecto.setCiudad(ciudad);


        try {

            session.save(proyecto);
            tx.commit();

            // Mensaje confirmación
            JOptionPane.showMessageDialog(null, "Se ha insertado correctamente el proyecto " + nombreProyecto.toUpperCase());


        } catch (TransientPropertyValueException e) {
            System.out.println("EL PROYECTO NO EXISTE");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("Propiedad:%s%n", e.getPropertyName());
        } catch (PersistentObjectException p) {
            //p.getMessage();
        } catch (ConstraintViolationException e) {
            System.out.println("PROYECTO DUPLICADO");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("COD ERROR:%d%n", e.getErrorCode());
            System.out.printf("ERROR SQL:%s%n", e.getSQLException().getMessage());
        }

        session.close();

    }


    public static void modificarProyecto(int IDmodificarProyecto, String nuevoNombre, String ciudad) {

        SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        Proyectos proyecto = new Proyectos();

        try {
            // Cargo el proveedor a modificar, eligiendo su ID
            proyecto = (Proyectos) session.load(Proyectos.class, IDmodificarProyecto);

            if (proyecto == null) {
                System.out.println("El proyecto no existe");

            } else {
                // Actualizo los datos que me interesen
                proyecto.setIdProyecto(IDmodificarProyecto);
                proyecto.setProyecto(nuevoNombre);
                proyecto.setCiudad(ciudad);


                // Actualiza, modifica el objeto
                session.update(proyecto);
                // Actualizo la base de datos
                tx.commit();

                JOptionPane.showMessageDialog(null, "Se han modificado correctamente el proyecto " + nuevoNombre.toUpperCase());

            }
        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE EL PROYECTO");
            JOptionPane.showMessageDialog(null, "No existe el proyecto " + nuevoNombre.toUpperCase(), "Error", JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
    }

    public static void borrarProyecto(int idProyecto) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // PROYECTO A BORRAR
        Proyectos proyecto = (Proyectos) session.load(Proyectos.class, idProyecto);
        try {

            if (proyecto != null) {

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {
                    session.delete(proyecto);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Se ha eliminado el proveedor " + proyecto.getProyecto().toUpperCase());
                }


            }


        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE EL PROYECTO");
        } catch (ConstraintViolationException c) {
            System.out.println("NO SE PUEDE ELIMINAR");
        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
        session.close();
    }


    public static ArrayList<Proyectos> consultaCodigoProyecto(String codigo) {

        // int idCodigo = Integer.parseInt(codigo); // No hace falta convertir el cdatop de la caja a int, para hacer la consulta, con el String vale

        ArrayList<Proyectos> listaProyectos = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROYECTOS QUE QUE INCLUYEN EN SU CÓDIGO: " + codigo);
        System.out.format("%-5s%-30s%-30s%n", "ID", "PROYECTO", "CIUDAD");


        Query lista = session.createQuery("from Proyectos where id_proyecto like '%" + codigo + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proyectos proyecto = (Proyectos) iter.next();
                System.out.format("%-5d%-30s%-30s%n", proyecto.getIdProyecto(), proyecto.getProyecto(), proyecto.getCiudad());

                listaProyectos.add(proyecto);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");
        session.close();

        return listaProyectos;

    }


    public static ArrayList<Proyectos> consultaNombreProyecto(String nombreProyecto) {


        ArrayList<Proyectos> listaProyectos = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROYECTOS QUE QUE INCLUYEN EN SU NOMBRE: " + nombreProyecto);
        System.out.format("%-5s%-30s%-30s%n", "ID", "PROYECTO", "NOMBRE", "CIUDAD");


        Query lista = session.createQuery("from Proyectos where proyecto  like '%" + nombreProyecto + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proyectos proyecto = (Proyectos) iter.next();
                System.out.format("%-5d%-30s%-30s%n", proyecto.getIdProyecto(), proyecto.getProyecto(), proyecto.getCiudad());

                listaProyectos.add(proyecto);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaProyectos;

    }


    public static ArrayList<Proyectos> consultaCiudadProyecto(String ciudadProyecto) {


        ArrayList<Proyectos> listaProyectos = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PROYECTOS QUE QUE INCLUYEN EN SU CIUDAD: " + ciudadProyecto);
        System.out.format("%-5s%-30s%-30s%n", "ID", "PROYECTO", "NOMBRE", "CIUDAD");


        Query lista = session.createQuery("from Proyectos where ciudad  like '%" + ciudadProyecto + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Proyectos proyecto = (Proyectos) iter.next();
                System.out.format("%-5d%-30s%-30s%n", proyecto.getIdProyecto(), proyecto.getProyecto(), proyecto.getCiudad());

                listaProyectos.add(proyecto);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaProyectos;

    }


    // **************************************************************** PIEZAS ***************************************************************


    public static ArrayList<Piezas> cargarPiezas() {

        ArrayList<Piezas> listaPiezas = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PIEZAS ACTUALIZADA:");
        System.out.format("%-5s%-40s%-40s%-30s%n", "ID", "PIEZA", "DESCRIPCIÓN", "PRECIO");

        Query lista = session.createQuery("from Piezas");

        // Obtenemos un Iterador y recorremos la lista
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Piezas pieza = (Piezas) iter.next();
                System.out.format("%-5d%-40s%-40s%.2f%n", pieza.getIdPieza(), pieza.getPieza(), pieza.getDescripcion(), pieza.getPrecio());

                listaPiezas.add(pieza);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaPiezas;

    }


    public static void insercionPieza(String nombrePieza, String descripcion, String precio) {

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // Instancio un proveedor nuevo
        Piezas pieza = new Piezas();

        pieza.setPieza(nombrePieza);
        pieza.setPrecio(Double.parseDouble(precio));
        pieza.setDescripcion(descripcion);


        try {

            session.save(pieza);
            tx.commit();

            // Mensaje confirmación
            JOptionPane.showMessageDialog(null, "Se ha insertado correctamente la pieza " + nombrePieza.toUpperCase());


        } catch (TransientPropertyValueException e) {
            System.out.println("LA PIEZA NO EXISTE");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("Propiedad:%s%n", e.getPropertyName());
        } catch (PersistentObjectException p) {
            //p.getMessage();
        } catch (ConstraintViolationException e) {
            System.out.println("PIEZA DUPLICADA");
            System.out.printf("MENSAJE:%s%n", e.getMessage());
            System.out.printf("COD ERROR:%d%n", e.getErrorCode());
            System.out.printf("ERROR SQL:%s%n", e.getSQLException().getMessage());
        }

        session.close();

    }


    public static void modificarPieza(int IDmodificarPieza, String nuevoNombre, String nuevaDescripcion, String nuevoPrecio) {

        SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();

        Piezas pieza = new Piezas();

        try {
            // Cargo el proveedor a modificar, eligiendo su ID
            pieza = (Piezas) session.load(Piezas.class, IDmodificarPieza);

            if (pieza == null) {
                System.out.println("La pieza no existe");

            } else {
                // Actualizo los datos que me interesen
                pieza.setIdPieza(IDmodificarPieza);
                pieza.setPieza(nuevoNombre);
                pieza.setDescripcion(nuevaDescripcion);
                pieza.setPrecio(Double.parseDouble(nuevoPrecio));

                // Actualiza, modifica el objeto
                session.update(pieza);
                // Actualizo la base de datos
                tx.commit();

                JOptionPane.showMessageDialog(null, "Se han modificado correctamente la pieza " + nuevoNombre.toUpperCase());

            }
        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE LA PIEZA");
            JOptionPane.showMessageDialog(null, "No existe el proveedor " + nuevoNombre.toUpperCase(), "Error", JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
    }


    public static void borrarPieza(int idPieza) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        // PROVEEDOR A BORRAR
        Piezas pieza = (Piezas) session.load(Piezas.class, idPieza);

        try {

            if (pieza != null) {

                int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {
                    session.delete(pieza);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Se ha eliminado la pieza " + pieza.getPieza().toUpperCase());
                }

            }


        } catch (ObjectNotFoundException o) {
            System.out.println("NO EXISTE la pieza");
        } catch (ConstraintViolationException c) {
            System.out.println("NO SE PUEDE ELIMINAR");
        } catch (Exception e) {
            System.out.println("ERROR NO CONTROLADO");
            //e.printStackTrace();
        }
        session.close();
    }


    public static ArrayList<Piezas> consultaCodigoPieza(String codigo) {

        // int idCodigo = Integer.parseInt(codigo); // No hace falta convertir el codigo de la caja a int, para hacer la consulta, con el String vale

        ArrayList<Piezas> listaPiezas = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===========================================================================================================================================");
        System.out.println("LISTA DE PIEZAS QUE QUE INCLUYEN EN SU CÓDIGO: " + codigo);
        System.out.format("%-5s%-30s%-30s%-30s%n", "ID", "NOMBRE", "DESCRIPCION", "PRECIO");


        Query lista = session.createQuery("from Piezas where id_pieza like '%" + codigo + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Piezas pieza = (Piezas) iter.next();
                System.out.format("%-5d%-30s%-30s%-30s%n", pieza.getIdPieza(), pieza.getPieza(), pieza.getDescripcion(), pieza.getPrecio());

                listaPiezas.add(pieza);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaPiezas;

    }


    public static ArrayList<Piezas> consultaNombrePieza(String nombrePieza) {


        ArrayList<Piezas> listaPiezas = new ArrayList<>();

        // Hay que hacer una sesion diferente por cada consulta, insercion, borrado, etc...
        Session session = sessionFactory.openSession();

        System.out.println("\n===================================================================================================================================================");
        System.out.println("LISTA DE PIEZAS QUE QUE INCLUYEN EN SU NOMBRE: " + nombrePieza);
        System.out.format("%-5s%-30s%-30s%-30s%n", "ID", "NOMBRE", "DESCRIPCION", "PRECIO");


        Query lista = session.createQuery("from Piezas where pieza  like '%" + nombrePieza + "%'");

        //lista.setParameter(1,idCodigo);
        // Obtenemos un Iterador y recorremos la list
        Iterator iter = lista.iterate();
        while (iter.hasNext()) {

            try {
                // Extraer el objeto
                Piezas pieza = (Piezas) iter.next();
                System.out.format("%-5d%-30s%-30s%-30s%n", pieza.getIdPieza(), pieza.getPieza(), pieza.getDescripcion(), pieza.getPrecio());

                listaPiezas.add(pieza);

            } catch (ClassCastException e) {
                //e.printStackTrace();
            }
        }

        System.out.println("\n===================================================================================================================================================");

        session.close();

        return listaPiezas;

    }


}
