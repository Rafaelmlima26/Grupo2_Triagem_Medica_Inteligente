import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
import joblib

# gerando um dataset sintetico baseado no protocolo de manchester
# a ideia eh simular dados reais de triagem pra treinar o modelo
np.random.seed(42)
n_amostras = 5000

dados = {
    # temperatura corporal em celsius
    'temperatura': np.random.uniform(35.0, 42.0, n_amostras),
    # batimentos por minuto
    'freq_cardiaca': np.random.uniform(40, 180, n_amostras),
    # pressao sistolica
    'pressao_sistolica': np.random.uniform(70, 200, n_amostras),
    # saturacao de oxigenio (SpO2)
    'saturacao_o2': np.random.uniform(70, 100, n_amostras),
    # escala de dor de 0 a 10
    'nivel_dor': np.random.randint(0, 11, n_amostras),
    # idade do paciente
    'idade': np.random.randint(0, 100, n_amostras),
}

df = pd.DataFrame(dados)

# funcao que classifica baseado nas faixas do protocolo de manchester
# nao eh perfeito mas da pra ter uma base boa pra treinar
def classificar_prioridade(row):
    # vermelho - emergencia: sinais criticos
    if (row['saturacao_o2'] < 85 or
        row['freq_cardiaca'] > 150 or row['freq_cardiaca'] < 45 or
        row['pressao_sistolica'] < 80 or
        row['temperatura'] > 41.0):
        return 0  # vermelho

    # laranja - muito urgente
    if (row['saturacao_o2'] < 90 or
        row['freq_cardiaca'] > 130 or
        row['pressao_sistolica'] < 90 or
        row['temperatura'] > 40.0 or
        row['nivel_dor'] >= 9):
        return 1  # laranja

    # amarelo - urgente
    if (row['saturacao_o2'] < 93 or
        row['freq_cardiaca'] > 110 or
        row['pressao_sistolica'] > 180 or
        row['temperatura'] > 39.0 or
        row['nivel_dor'] >= 7):
        return 2  # amarelo

    # verde - pouco urgente
    if (row['nivel_dor'] >= 4 or
        row['temperatura'] > 38.0 or
        row['freq_cardiaca'] > 100):
        return 3  # verde

    # azul - nao urgente, paciente estavel
    return 4  # azul

df['prioridade'] = df.apply(classificar_prioridade, axis=1)

# separando treino e teste
X = df.drop('prioridade', axis=1)
y = df['prioridade']
X_treino, X_teste, y_treino, y_teste = train_test_split(X, y, test_size=0.2, random_state=42)

# treinando com random forest pq funciona bem pra esse tipo de classificacao
modelo = RandomForestClassifier(n_estimators=100, random_state=42)
modelo.fit(X_treino, y_treino)

# vendo como ficou a acuracia
y_pred = modelo.predict(X_teste)
print("=== Resultado do treinamento ===")
print(classification_report(y_teste, y_pred,
    target_names=['Vermelho', 'Laranja', 'Amarelo', 'Verde', 'Azul']))

# salvando o modelo pra usar na API
joblib.dump(modelo, 'model.joblib')
print("Modelo salvo em model.joblib")
