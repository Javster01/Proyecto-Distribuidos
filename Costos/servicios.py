from flask import Flask, jsonify

app = Flask(__name__)

# Datos para productos
productos_data = [
    {
        "id": 1234,
        "nombre": "Producto A",
        "precio": 99.99,
        "cantidad": 50
    },
    {
        "id": 5678,
        "nombre": "Producto B",
        "precio": 49.99,
        "cantidad": 30
    },
    {
        "id": 9101,
        "nombre": "Producto C",
        "precio": 19.99,
        "cantidad": 100
    },
    {
        "id": 1121,
        "nombre": "Producto D",
        "precio": 29.99,
        "cantidad": 75
    },
    {
        "id": 3141,
        "nombre": "Producto E",
        "precio": 59.99,
        "cantidad": 20
    },{
        "id": 3142,
        "nombre": "Producto F",
        "precio": 59.99,
        "cantidad": 20
    }
]

# Datos para ventas
ventas_data = [
    {
        "idFactura": 2,
        "fecha": "2024-08-02T00:00:00.000+00:00",
        "cliente": "Juan Pérez",
        "detalleProductos": [
            {
                "id": 1234,
                "nombreProducto": "Producto A",
                "cantidadProducto": 1,
                "precioUnitarioProducto": 99.99,
                "precioTotalProducto": 99.99
            },
            {
                "id": 5678,
                "nombreProducto": "Producto B",
                "cantidadProducto": 2,
                "precioUnitarioProducto": 49.99,
                "precioTotalProducto": 99.98
            }
        ],
        "totalFactura": 199.97,
        "dineroRecibido": 200.00,
        "cambio": 0.03
    },
    {
        "idFactura": 3,
        "fecha": "2024-08-02T00:00:00.000+00:00",
        "cliente": "Maria",
        "detalleProductos": [
            {
                "id": 9101,
                "nombreProducto": "Producto C",
                "cantidadProducto": 5,
                "precioUnitarioProducto": 19.99,
                "precioTotalProducto": 99.95
            },
            {
                "id": 1121,
                "nombreProducto": "Producto D",
                "cantidadProducto": 2,
                "precioUnitarioProducto": 29.99,
                "precioTotalProducto": 59.98
            }
        ],
        "totalFactura": 159.93,
        "dineroRecibido": 160.00,
        "cambio": 0.07
    },
    {
        "idFactura": 4,
        "fecha": "2024-08-03T00:00:00.000+00:00",
        "cliente": "Carlos",
        "detalleProductos": [
            {
                "id": 3141,
                "nombreProducto": "Producto E",
                "cantidadProducto": 3,
                "precioUnitarioProducto": 59.99,
                "precioTotalProducto": 179.97
            }
        ],
        "totalFactura": 179.97,
        "dineroRecibido": 180.00,
        "cambio": 0.03
    },
    {
        "idFactura": 5,
        "fecha": "2024-08-03T00:00:00.000+00:00",
        "cliente": "Lucia",
        "detalleProductos": [
            {
                "id": 5678,
                "nombreProducto": "Producto B",
                "cantidadProducto": 1,
                "precioUnitarioProducto": 49.99,
                "precioTotalProducto": 49.99
            },
            {
                "id": 9101,
                "nombreProducto": "Producto C",
                "cantidadProducto": 3,
                "precioUnitarioProducto": 19.99,
                "precioTotalProducto": 59.97
            }
        ],
        "totalFactura": 109.96,
        "dineroRecibido": 110.00,
        "cambio": 0.04
    }
]

# Datos para facturas
facturas_data = [
    {
        "id_compra": 1,
        "id_producto": 1234,
        "cantidad": 10,
        "precio_unitario": 19.99,
        "precio_total": 199.90,
        "fecha": "2024-08-01"
    },
    {
        "id_compra": 2,
        "id_producto": 5678,
        "cantidad": 5,
        "precio_unitario": 49.99,
        "precio_total": 249.95,
        "fecha": "2024-08-01"
    },
    {
        "id_compra": 3,
        "id_producto": 9101,
        "cantidad": 7,
        "precio_unitario": 29.99,
        "precio_total": 209.93,
        "fecha": "2024-08-01"
    }
]

@app.route('/productos', methods=['GET'])
def get_productos():
    return jsonify(productos_data)

@app.route('/ventas', methods=['GET'])
def get_ventas():
    return jsonify(ventas_data)

@app.route('/compras', methods=['GET'])
def get_facturas():
    return jsonify(facturas_data)

if __name__ == '__main__':
    app.run(debug=True)