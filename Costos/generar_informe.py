# Librerías e importaciones necesarias
from flask import Flask, render_template, send_file
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import datetime
import os
import httpx
import asyncio
from fpdf import FPDF

# Inicializa la aplicación Flask
app = Flask(__name__)

# Funciones asíncronas para obtener datos desde APIs

async def fetch_compras():
    url = 'http://localhost:5000/compras'
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status() 
        data = response.json()
        return data

async def fetch_ventas():
    url = "http://10.200.2.96:8080/api/facturas"
    try:
        async with httpx.AsyncClient() as client:
            response = await client.get(url)
            response.raise_for_status()
            data = response.json()
            return data
    except httpx.RequestError as exc:
        print(f"Error de solicitud: {exc}")
    except httpx.HTTPStatusError as exc:
        print(f"Error de estado HTTP: {exc}")
    except Exception as exc:
        print(f"Error inesperado: {exc}")

async def fetch_inventario():
    url = "http://10.200.3.132:5000/api/get_productos"
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status()
        data = response.json()
        return data

async def obtener_datos():
    datos_compras = await fetch_compras()
    datos_ventas = await fetch_ventas()
    datos_inventario = await fetch_inventario()
    return datos_inventario, datos_compras, datos_ventas

# Clase para crear PDFs utilizando FPDF
class PDF(FPDF):
    def header(self):
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, 'Informe de Costos', 0, 1, 'C')

    def footer(self):
        self.set_y(-15)
        self.set_font('Arial', 'I', 8)
        self.cell(0, 10, f'Page {self.page_no()}', 0, 0, 'C')

    def chapter_title(self, title):
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, title, 0, 1, 'L')
        self.ln(10)

    def chapter_body(self, body):
        self.set_font('Arial', '', 12)
        self.multi_cell(0, 10, body)
        self.ln()

    def add_image(self, image_path):
        self.image(image_path, x=10, y=None, w=190)
        self.ln(10)

    def add_table(self, data, columns):
        self.set_font('Arial', 'B', 8)
        col_widths = [self.get_string_width(col) + 8 for col in columns]
        for col, width in zip(columns, col_widths):
            self.cell(width, 8, col, 1, 0, 'C')
        self.ln()

        self.set_font('Arial', '', 10)
        for row in data:
            for item, width in zip(row, col_widths):
                self.cell(width, 10, str(item), 1, 0, 'C')
            self.ln()

def get_next_invoice_id():
    filename = 'last_invoice_id.txt'
    if os.path.exists(filename):
        with open(filename, 'r') as file:
            last_id = file.read().strip()
            next_id = int(last_id) + 1 if last_id else 1
    else:
        next_id = 1

    with open(filename, 'w') as file:
        file.write(str(next_id))

    return next_id

def calcular_costos_y_ganancias(datos_compras, datos_ventas, gastos_generales):
    costos_compra = sum(compra['precio_total'] for compra in datos_compras)
    costos_venta = sum(sum(detalle['precioTotalProducto'] * 0.7 for detalle in venta['detalleProductos']) for venta in datos_ventas)
    total_ventas = sum(sum(detalle['precioTotalProducto'] for detalle in venta['detalleProductos']) for venta in datos_ventas)
    ganancias = total_ventas - (costos_compra + costos_venta + gastos_generales)
    
    return {
        'costos_compra': costos_compra,
        'costos_venta': costos_venta,
        'gastos_generales': gastos_generales,
        'ganancias': ganancias,
        'total_ventas': total_ventas
    }

def generate_pdf_with_graphics(data_compras, plot_data_compras, table_data_compras, 
                               data_ventas, plot_data_ventas, total_ventas, total_compras):
    pdf = PDF()
    
    # Informe de Compras
    pdf.add_page()
    pdf.chapter_title('Informe de Compras')
    pdf.chapter_body(data_compras)
    
    pdf.add_page()
    pdf.chapter_title('Resumen de Compras')
    pdf.chapter_body(f'Total Comprado: ${total_compras:.2f}')
    
    # Gráfico de pastel de compras
    plt.figure(figsize=(6, 4))
    plt.pie(plot_data_compras['values'], labels=plot_data_compras['labels'], autopct='%1.1f%%', startangle=140)
    plt.title('Gráfico de Compras')
    pie_plot_path = 'static/pie_plot_compras.png'
    plt.savefig(pie_plot_path)
    plt.close()
    pdf.add_image(pie_plot_path)
    
    # Gráfico de barras de compras
    plt.figure(figsize=(6, 4))
    plt.bar(plot_data_compras['labels'], plot_data_compras['values'], color='orange')
    plt.title('Gráfico de Barras de Compras')
    plt.xlabel('Productos')
    plt.ylabel('Cantidad Comprada')
    plt.xticks(rotation=45, ha='right')
    plt.grid(True)
    bar_plot_path = 'static/bar_plot_compras.png'
    plt.savefig(bar_plot_path)
    plt.close()
    pdf.add_image(bar_plot_path)
    
    # Informe de Ventas
    pdf.add_page()
    pdf.chapter_title('Informe de Ventas')
    pdf.chapter_body(data_ventas)
    
    pdf.add_page()
    pdf.chapter_title('Resumen de Ventas')
    pdf.chapter_body(f'Total Vendido: ${total_ventas:.2f}')
    
    # Gráfico de pastel de ventas
    plt.figure(figsize=(6, 4))
    plt.pie(plot_data_ventas['values'], labels=plot_data_ventas['labels'], autopct='%1.1f%%', startangle=140)
    plt.title('Gráfico de Ventas')
    pie_plot_path = 'static/pie_plot_ventas.png'
    plt.savefig(pie_plot_path)
    plt.close()
    pdf.add_image(pie_plot_path)
    
    # Gráfico de barras de ventas
    plt.figure(figsize=(6, 4))
    plt.bar(plot_data_ventas['labels'], plot_data_ventas['values'], color='blue')
    plt.title('Gráfico de Barras de Ventas')
    plt.xlabel('Productos')
    plt.ylabel('Cantidad Vendida')
    plt.xticks(rotation=45, ha='right')
    plt.grid(True)
    bar_plot_path = 'static/bar_plot_ventas.png'
    plt.savefig(bar_plot_path)
    plt.close()
    pdf.add_image(bar_plot_path)

    # Tabla
    pdf.add_page()
    table_columns = ['Nombre Producto', 'Precio Total Compra', 'Precio Total Venta', 'Cantidad Vendida', 'Rentabilidad', 'Fecha Venta']
    pdf.add_table(table_data_compras, table_columns)
    
    timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    pdf_filename = f'informe_completo_{get_next_invoice_id()}_{timestamp}.pdf'
    pdf_path = os.path.join('static', pdf_filename)
    pdf.output(pdf_path)
    
    return pdf_filename
async def fetch_facturas():
    url = "http://10.200.4.78:8080/api/facturas"
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status()
        return response.json()
        
@app.route('/')
async def index():
    datos_inventario, datos_compras, datos_ventas = await obtener_datos()

    fecha_antigua = min(venta['fecha'].split('T')[0] for venta in datos_ventas)
    fecha_ultima = max(venta['fecha'].split('T')[0] for venta in datos_ventas)
    
    gastos_generales = 1000
    
    resumen_financiero = calcular_costos_y_ganancias(datos_compras, datos_ventas, gastos_generales)
    
    table_data_compras = []
    total_ventas = 0.0
    total_compras = 0.0
    for venta in datos_ventas:
        for detalle in venta['detalleProductos']:
            for compra in datos_compras:
                if compra['id_producto'] == detalle['id']:
                    rentabilidad = detalle['precioTotalProducto'] - compra['precio_total']
                    row = [
                        detalle['nombreProducto'],
                        compra['precio_total'],
                        detalle['precioTotalProducto'],
                        detalle['cantidadProducto'],
                        rentabilidad,
                        venta['fecha'].split('T')[0]
                    ]
                    table_data_compras.append(row)
                    total_ventas += detalle['precioTotalProducto']
                    total_compras += compra['precio_total']
    
    productos_compras = {}
    for compra in datos_compras:
        producto = next((p['nombre'] for p in datos_inventario if p['id_producto'] == compra['id_producto']), 'Desconocido')
        cantidad = compra['cantidad']
        if producto in productos_compras:
            productos_compras[producto] += cantidad
        else:
            productos_compras[producto] = cantidad

    plot_data_compras = {
        'labels': list(productos_compras.keys()),
        'values': list(productos_compras.values())
    }

    productos_ventas = {}
    for venta in datos_ventas:
        for detalle in venta['detalleProductos']:
            producto = detalle['nombreProducto']
            cantidad = detalle['cantidadProducto']
            if producto in productos_ventas:
                productos_ventas[producto] += cantidad
            else:
                productos_ventas[producto] = cantidad

    plot_data_ventas = {
        'labels': list(productos_ventas.keys()),
        'values': list(productos_ventas.values())
    }

    data_compras = (
        f'Fecha: {datetime.datetime.now().strftime("%Y-%m-%d")}\n'
        f'ID Informe: {get_next_invoice_id()}\n'
        'Cliente: Empresa XYZ'
    )
    
    data_ventas = (
        f'Fecha: {datetime.datetime.now().strftime("%Y-%m-%d")}\n'
        f'ID Informe: {get_next_invoice_id()}\n'
        'Cliente: Empresa XYZ'
    )
    
    pdf_filename = generate_pdf_with_graphics(data_compras, plot_data_compras, table_data_compras, 
                                              data_ventas, plot_data_ventas, total_ventas, total_compras)
    return render_template(
        'index.html',
        pdf_filename=pdf_filename,
        current_date=datetime.datetime.now().strftime("%Y-%m-%d"),
        invoice_id=get_next_invoice_id(),
        table_data=table_data_compras,
        fecha_antigua=fecha_antigua,
        fecha_ultima=fecha_ultima,
        resumen_financiero=resumen_financiero
    )

@app.route('/download/<filename>')
def download_file(filename):
    return send_file(os.path.join('static', filename), as_attachment=True)

if __name__ == '__main__':
    app.run(debug=True, port=5001)
