FROM python:3.11-slim

WORKDIR /app

# instalando as dependencias primeiro pra aproveitar o cache do docker
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# treinando o modelo na hora do build pra ja ficar pronto
RUN python train_model.py

EXPOSE 8000

CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "8000"]
