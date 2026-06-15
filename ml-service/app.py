from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import joblib
import numpy as np

app = FastAPI(title="Triagem ML Service", version="1.0.0")

# carregando o modelo que foi treinado no train_model.py
modelo = joblib.load("model.joblib")

# mapeamento das classes pra ficar mais legivel no retorno
PRIORIDADES = {
    0: {"nivel": "Vermelho", "descricao": "Emergencia - Atendimento imediato"},
    1: {"nivel": "Laranja", "descricao": "Muito Urgente - Ate 10 minutos"},
    2: {"nivel": "Amarelo", "descricao": "Urgente - Ate 60 minutos"},
    3: {"nivel": "Verde", "descricao": "Pouco Urgente - Ate 120 minutos"},
    4: {"nivel": "Azul", "descricao": "Nao Urgente - Ate 240 minutos"},
}

# esquema dos dados que a API vai receber
class SinaisVitais(BaseModel):
    temperatura: float
    freq_cardiaca: float
    pressao_sistolica: float
    saturacao_o2: float
    nivel_dor: int
    idade: int

# esquema da resposta
class Predicao(BaseModel):
    prioridade: str
    descricao: str
    codigo: int


@app.get("/health")
def health_check():
    """endpoint basico pra saber se o servico ta rodando"""
    return {"status": "ok"}


@app.post("/predict", response_model=Predicao)
def predizer_prioridade(sinais: SinaisVitais):
    """recebe os sinais vitais e retorna a classificacao de prioridade"""
    try:
        # montando o array na mesma ordem que o modelo foi treinado
        entrada = np.array([[
            sinais.temperatura,
            sinais.freq_cardiaca,
            sinais.pressao_sistolica,
            sinais.saturacao_o2,
            sinais.nivel_dor,
            sinais.idade
        ]])

        resultado = modelo.predict(entrada)[0]
        info = PRIORIDADES[resultado]

        return Predicao(
            prioridade=info["nivel"],
            descricao=info["descricao"],
            codigo=resultado
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erro na predicao: {str(e)}")
