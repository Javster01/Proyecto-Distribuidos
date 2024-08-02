#Librerias e Importaciones necesarias
from flask import Flask, render_template, send_file
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import datetime
import os
import httpx
import asyncio
from fpdf import FPDF

#Iinicializacion de la aplicacion Flask
app = Flask(__name__)

# Copia de las funciones de costos.py para obtener datos asincrónicamente
async def fetch_compras():
    """
    Recupera los datos de compras desde un servidor web.

    Esta función realiza una solicitud HTTP GET al endpoint 
    '/compras' en el servidor local que se ejecuta en el puerto 5000. 
    Utiliza un cliente HTTP asíncrono para hacer la solicitud y 
    espera la respuesta.

    La función realiza las siguientes acciones:
    1. Define la URL del endpoint de compras.
    2. Crea un cliente HTTP asíncrono para enviar la solicitud.
    3. Realiza la solicitud GET a la URL definida.
    4. Verifica si la solicitud fue exitosa (lanza una excepción si no lo fue).
    5. Extrae y devuelve los datos de la respuesta en formato JSON.

    Returns:
        dict: Los datos de compras en formato JSON, que se espera que 
              sea un diccionario de Python.
    
    Raises:
        httpx.HTTPStatusError: Si la respuesta del servidor indica un 
                               error HTTP (código de estado 4xx o 5xx).
    """
    url = 'http://localhost:5000/compras'
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status()  # Lanza una excepción si el código de estado no es 2xx
        data = response.json()      # Convierte la respuesta a JSON
        return data                # Devuelve los datos JSON

async def fetch_ventas():
    """
    Recupera los datos de ventas desde un servidor web.

    Esta función realiza una solicitud HTTP GET al endpoint 
    '/ventas' en el servidor local que se ejecuta en el puerto 5000. 
    Utiliza un cliente HTTP asíncrono para hacer la solicitud y 
    espera la respuesta.

    La función realiza las siguientes acciones:
    1. Define la URL del endpoint de ventas.
    2. Crea un cliente HTTP asíncrono para enviar la solicitud.
    3. Realiza la solicitud GET a la URL definida.
    4. Verifica si la solicitud fue exitosa (lanza una excepción si no lo fue).
    5. Extrae y devuelve los datos de la respuesta en formato JSON.

    Returns:
        dict: Los datos de ventas en formato JSON, que se espera que 
              sea un diccionario de Python.
    
    Raises:
        httpx.HTTPStatusError: Si la respuesta del servidor indica un 
                               error HTTP (código de estado 4xx o 5xx).
    """

    url = "http://localhost:5000/ventas"
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status()# Lanza una excepción si el código de estado no es 2xx
        data = response.json()# Convierte la respuesta a JSON
        return data

async def fetch_inventario():
    """
    Recupera los datos del inventario de productos desde un servidor web.

    Esta función realiza una solicitud HTTP GET al endpoint 
    '/productos' en el servidor local que se ejecuta en el puerto 5000. 
    Utiliza un cliente HTTP asíncrono para hacer la solicitud y 
    espera la respuesta.

    La función realiza las siguientes acciones:
    1. Define la URL del endpoint de productos.
    2. Crea un cliente HTTP asíncrono para enviar la solicitud.
    3. Realiza la solicitud GET a la URL definida.
    4. Verifica si la solicitud fue exitosa (lanza una excepción si no lo fue).
    5. Extrae y devuelve los datos de la respuesta en formato JSON.

    Returns:
        dict: Los datos del inventario de productos en formato JSON, que se 
              espera que sea un diccionario de Python.
    
    Raises:
        httpx.HTTPStatusError: Si la respuesta del servidor indica un 
                               error HTTP (código de estado 4xx o 5xx).
    """

    url = "http://localhost:5000/productos"
    async with httpx.AsyncClient() as client:
        response = await client.get(url)
        response.raise_for_status()# Lanza una excepción si el código de estado no es 2xx
        data = response.json()# Convierte la respuesta a JSON
        return data

#Obtiene todos los datos de las API 
async def obtener_datos():
    """
    Recupera los datos de compras, ventas e inventario desde el servidor web.

    Esta función coordina la recuperación de datos desde tres endpoints diferentes:
    '/compras', '/ventas', y '/productos'. Utiliza funciones asíncronas para 
    realizar solicitudes HTTP GET a estos endpoints y espera las respuestas.

    La función realiza las siguientes acciones:
    1. Llama a `fetch_compras()` para obtener los datos de compras.
    2. Llama a `fetch_ventas()` para obtener los datos de ventas.
    3. Llama a `fetch_inventario()` para obtener los datos del inventario.
    4. Devuelve una tupla que contiene los datos del inventario, las compras y las ventas en ese orden.

    Returns:
        tuple: Una tupla con tres elementos:
            - dict: Los datos del inventario de productos en formato JSON.
            - dict: Los datos de compras en formato JSON.
            - dict: Los datos de ventas en formato JSON.
    """
    datos_compras = await fetch_compras()       # Obtiene los datos de compras
    datos_ventas = await fetch_ventas()         # Obtiene los datos de ventas
    datos_inventario = await fetch_inventario() # Obtiene los datos del inventario
    return datos_inventario, datos_compras, datos_ventas # Devuelve los datos en una tupla

#Clase personalizada para crear PDF
class PDF(FPDF):
    """
    Clase para crear informes PDF personalizados utilizando FPDF.

    Esta clase extiende la clase FPDF para incluir funcionalidades personalizadas 
    como encabezados, pies de página, y métodos para agregar títulos de capítulos, 
    contenido, imágenes y tablas al documento PDF.

    Métodos:
        header(): Agrega un encabezado en la parte superior de cada página.
        footer(): Agrega un pie de página en la parte inferior de cada página.
        chapter_title(title): Agrega un título de capítulo al documento.
        chapter_body(body): Agrega el cuerpo del capítulo al documento.
        add_image(image_path): Agrega una imagen al documento.
        add_table(data, columns): Agrega una tabla al documento con datos y encabezados de columna.
    """

    def header(self):
        """
        Agrega un encabezado en la parte superior de cada página.

        Establece la fuente a Arial en negrita de tamaño 12 y añade el texto 'Informe de Costos' 
        centrado en la parte superior de la página.
        """
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, 'Informe de Costos', 0, 1, 'C')

    def footer(self):
        """
        Agrega un pie de página en la parte inferior de cada página.

        Establece la posición Y en -15 para colocar el pie de página en la parte inferior 
        de la página y añade el número de página centrado en la parte inferior.
        """
        self.set_y(-15)
        self.set_font('Arial', 'I', 8)
        self.cell(0, 10, f'Page {self.page_no()}', 0, 0, 'C')

    def chapter_title(self, title):
        """
        Agrega un título de capítulo al documento.

        Establece la fuente a Arial en negrita de tamaño 12 y añade el título del capítulo 
        alineado a la izquierda, seguido de un salto de línea.

        Parámetros:
            title (str): El título del capítulo a agregar.
        """
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, title, 0, 1, 'L')
        self.ln(10)

    def chapter_body(self, body):
        """
        Agrega el cuerpo del capítulo al documento.

        Establece la fuente a Arial de tamaño 12 y añade el contenido del capítulo 
        utilizando celdas de múltiple línea para permitir texto largo.

        Parámetros:
            body (str): El contenido del capítulo a agregar.
        """
        self.set_font('Arial', '', 12)
        self.multi_cell(0, 10, body)
        self.ln()

    def add_image(self, image_path):
        """
        Agrega una imagen al documento.

        Inserta una imagen en el documento en la posición X=10, con un ancho de 190. 
        La posición Y se ajusta automáticamente.

        Parámetros:
            image_path (str): La ruta al archivo de imagen que se desea agregar.
        """
        self.image(image_path, x=10, y=None, w=190)
        self.ln(10)

    def add_table(self, data, columns):
        """
        Agrega una tabla al documento con datos y encabezados de columna.

        Establece la fuente a Arial en negrita de tamaño 12 para los encabezados de columna 
        y a Arial de tamaño 12 para los datos de la tabla. La tabla se dibuja con bordes 
        simples.

        Parámetros:
            data (list of list): Los datos de la tabla a agregar, donde cada sublista representa 
                                  una fila de la tabla.
            columns (list of str): Los nombres de las columnas de la tabla.
        """
        self.set_font('Arial', 'B', 12)
        for col in columns:
            self.cell(40, 10, col, 1)
        self.ln()

        self.set_font('Arial', '', 12)
        for row in data:
            for item in row:
                self.cell(40, 10, str(item), 1)
            self.ln()

def get_next_invoice_id():
    """
    Obtiene el siguiente ID de factura y actualiza el archivo de seguimiento.

    Esta función lee el último ID de factura desde un archivo de texto llamado 
    'last_invoice_id.txt', incrementa el valor leído y guarda el nuevo ID en el 
    archivo. Si el archivo no existe o el contenido no es un número válido, 
    se inicia con el ID 1.

    La función realiza las siguientes acciones:
    1. Verifica si el archivo 'last_invoice_id.txt' existe.
    2. Si el archivo existe, lee su contenido y trata de convertirlo a un número entero.
       - Si la conversión falla (por ejemplo, el contenido no es un número), se reinicia el ID a 0.
    3. Si el archivo no existe o el contenido está vacío, se inicia con el ID 0.
    4. Incrementa el ID leído (o inicializado) en 1 para obtener el siguiente ID.
    5. Guarda el nuevo ID en el archivo 'last_invoice_id.txt'.
    6. Devuelve el siguiente ID de factura.

    Returns:
        int: El siguiente ID de factura.
    """
    filename = 'last_invoice_id.txt'
    
    if os.path.exists(filename):
        with open(filename, 'r') as file:
            content = file.read().strip()
            if content:
                try:
                    last_id = int(content)  # Intenta convertir el contenido a un entero
                except ValueError:
                    last_id = 0  # Si la conversión falla, reinicia el ID a 0
            else:
                last_id = 0  # Si el contenido está vacío, reinicia el ID a 0
    else:
        last_id = 0  # Si el archivo no existe, inicia con el ID 0
    
    next_id = last_id + 1  # Incrementa el último ID para obtener el siguiente ID
    
    with open(filename, 'w') as file:
        file.write(str(next_id))  # Guarda el nuevo ID en el archivo
    
    return next_id  # Devuelve el siguiente ID de factura

def generate_pdf_with_graphics(report_type, data, plot_data, table_data):
    """
    Genera un informe PDF con gráficos y tabla según el tipo de informe especificado.

    Esta función crea un documento PDF que incluye:
    - Un título de capítulo basado en el tipo de informe ('ventas' o 'compras').
    - Cuerpo del informe con datos personalizados.
    - Un gráfico de pastel y un gráfico de barras generados a partir de los datos de gráficos proporcionados.
    - Una tabla con datos proporcionados.
    - El archivo PDF se guarda en el directorio 'static' con un nombre que incluye el tipo de informe, ID de factura y una marca de tiempo.

    Parámetros:
        report_type (str): Tipo de informe, debe ser 'ventas' o 'compras'.
        data (str): Contenido del cuerpo del informe, con marcadores de posición 'FECHA_ACTUAL' e 'ID_FACTURA'.
        plot_data (dict): Datos para los gráficos. Debe contener:
            - 'x': Etiquetas o categorías.
            - 'y': Valores correspondientes.
        table_data (list of list): Datos para la tabla, donde cada sublista representa una fila de la tabla.

    Returns:
        str: Nombre del archivo PDF generado, o None si ocurrió un error.
    """
    pdf = PDF()
    pdf.add_page()

    try:
        # Establece el título del informe según el tipo de reporte
        if report_type == 'ventas':
            pdf.chapter_title('Informe de Ventas')
        elif report_type == 'compras':
            pdf.chapter_title('Informe de Compras')
        else:
            raise ValueError("Tipo de informe no válido. Usa 'ventas' o 'compras'.")

        # Obtiene el ID de factura y la fecha actual, y reemplaza los marcadores en los datos del informe
        invoice_id = get_next_invoice_id()
        current_date = datetime.datetime.now().strftime("%Y-%m-%d")
        data = data.replace('FECHA_ACTUAL', current_date).replace('ID_FACTURA', str(invoice_id))
        pdf.chapter_body(data)

        # Asegura que el directorio 'static' exista
        if not os.path.exists('static'):
            os.makedirs('static')

        # Genera un gráfico de pastel
        plt.figure(figsize=(8, 6))
        plt.pie(plot_data['y'], labels=plot_data['x'], autopct='%1.1f%%', startangle=140)
        plt.title(f'Gráfico de {report_type.capitalize()}')
        pie_plot_path = 'static/plot.png'
        plt.savefig(pie_plot_path)
        plt.close()
        pdf.add_image(pie_plot_path)

        # Genera un gráfico de barras
        plt.figure(figsize=(8, 6))
        plt.bar(plot_data['x'], plot_data['y'], color='orange')
        plt.title(f'Gráfico de Barras de {report_type.capitalize()}')
        plt.xlabel('Categorías')
        plt.ylabel('Valores')
        plt.grid(True)
        bar_plot_path = 'static/bar_plot.png'
        plt.savefig(bar_plot_path)
        plt.close()
        pdf.add_image(bar_plot_path)

        # Agrega una página nueva para la tabla y la incluye en el PDF
        pdf.add_page()
        table_columns = ['Categoría', 'Valor', 'Descripción']
        pdf.add_table(table_data, table_columns)

        # Define el nombre del archivo PDF y guarda el documento
        timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
        pdf_filename = f'informe_costos_{report_type}_{invoice_id}_{timestamp}.pdf'
        pdf_path = os.path.join('static', pdf_filename)
        pdf.output(pdf_path)
        
        return pdf_filename

    except Exception as e:
        print(f"Error al generar el PDF: {e}")
        return None

def generate_graphs(plot_data):
    """
    Genera dos gráficos (uno de pastel y uno de barras) y guarda los archivos de imagen en el directorio 'static'.

    Esta función crea y guarda dos gráficos a partir de los datos proporcionados:
    1. Un gráfico de pastel que muestra el porcentaje de cantidades vendidas por mes.
    2. Un gráfico de barras que muestra la cantidad de unidades vendidas por mes.

    La función realiza las siguientes acciones:
    1. Verifica si el directorio 'static' existe; si no, lo crea.
    2. Genera un gráfico de pastel con los datos de ventas y guarda la imagen en 'static/pie_chart.png'.
    3. Genera un gráfico de barras con los datos de ventas y guarda la imagen en 'static/bar_chart.png'.
    4. Devuelve las rutas de los archivos de imagen generados.

    Parámetros:
        plot_data (dict): Datos para los gráficos. Debe contener:
            - 'x': Etiquetas o categorías (e.g., meses).
            - 'y': Valores correspondientes (e.g., cantidades vendidas).

    Returns:
        tuple: Una tupla con dos elementos:
            - str: Ruta del archivo del gráfico de pastel.
            - str: Ruta del archivo del gráfico de barras.
    """
    if not os.path.exists('static'):
        os.makedirs('static')  # Crea el directorio 'static' si no existe

    # Genera el gráfico de pastel
    plt.figure(figsize=(8, 6))
    plt.pie(plot_data['y'], labels=plot_data['x'], autopct='%1.1f%%', startangle=140)
    plt.title('Porcentaje de Cantidades Vendidas por Mes')
    pie_plot_path = 'static/pie_chart.png'
    plt.savefig(pie_plot_path)  # Guarda el gráfico de pastel
    plt.close()  # Cierra la figura actual

    # Genera el gráfico de barras
    plt.figure(figsize=(8, 6))
    plt.bar(plot_data['x'], plot_data['y'], color='blue')
    plt.title('Cantidad de Unidades Vendidas por Mes')
    plt.xlabel('Meses')
    plt.ylabel('Unidades Vendidas')
    plt.grid(True)
    bar_plot_path = 'static/bar_chart.png'
    plt.savefig(bar_plot_path)  # Guarda el gráfico de barras
    plt.close()  # Cierra la figura actual

    return pie_plot_path, bar_plot_path  # Devuelve las rutas de los archivos de imagen


@app.route('/')
async def index():
    """
    Maneja la ruta principal y genera un informe PDF con gráficos y datos tabulares.

    Esta función realiza lo siguiente:
    1. Obtiene los datos de inventario, compras y ventas de manera asíncrona.
    2. Prepara los datos para la tabla en el informe, calculando la rentabilidad para cada venta.
    3. Define los datos para los gráficos (ventas por mes).
    4. Genera gráficos de pastel y barras y guarda las imágenes en el directorio 'static'.
    5. Define el contenido del informe PDF con la información de ventas y detalles adicionales.
    6. Genera un archivo PDF con gráficos y tabla utilizando los datos y gráficos generados.
    7. Renderiza la plantilla 'index.html' y pasa el nombre del archivo PDF generado a la plantilla.

    Returns:
        str: Renderiza la plantilla 'index.html' con el nombre del archivo PDF generado.
    """
    # Obtiene datos de inventario, compras y ventas de manera asíncrona
    datos_inventario, datos_compras, datos_ventas = await obtener_datos()

    # Prepara los datos para la tabla en el informe
    table_data = []
    for venta in datos_ventas:
        for detalle in venta['detalleProductos']:
            for compra in datos_compras:
                if compra['id_producto'] == detalle['id']:
                    # Calcula la rentabilidad como diferencia entre precio de venta y precio de compra
                    rentabilidad = detalle['precioTotalProducto'] - compra['precio_total']
                    # Agrega una fila a los datos de la tabla
                    row = [
                        detalle['nombreProducto'],
                        compra['precio_total'],
                        detalle['precioTotalProducto'],
                        detalle['cantidadProducto'],
                        rentabilidad,
                        venta['fecha'].split('T')[0]  # Extrae solo la fecha (sin hora)
                    ]
                    table_data.append(row)

    # Define los datos para los gráficos
    meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo']
    ventas_por_mes = [200, 300, 250, 400, 350]
    plot_data = {
        'x': meses,
        'y': ventas_por_mes
    }

    # Genera gráficos y guarda las imágenes en el directorio 'static'
    pie_plot_path, bar_plot_path = generate_graphs(plot_data)

    # Define el tipo de informe y el contenido del informe PDF
    report_type = 'ventas'
    data = (
        'Fecha: FECHA_ACTUAL\n'
        'ID Informe: ID_INFORME\n'
        'Cliente: Empresa XYZ\n'
        'Producto: Producto A\n'
        'Cantidad: 10\n'
        'Precio Unitario: $100\n'
        'Total: $1000\n\n'
        'Detalles adicionales aquí...'
    )

    # Genera el PDF con gráficos y datos tabulares
    pdf_filename = generate_pdf_with_graphics(report_type, data, plot_data, table_data)

    # Renderiza la plantilla 'index.html' y pasa el nombre del archivo PDF generado
    return render_template(
        'index.html',
        pdf_filename=pdf_filename,
    )