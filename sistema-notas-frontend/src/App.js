import React, { useState, useEffect } from 'react';
import './index.css';

function App() {
  const [alunos, setAlunos] = useState([]);
  const [nome, setNome] = useState('');
  const [notas, setNotas] = useState(['', '', '', '', '']);
  const [frequencia, setFrequencia] = useState('');
  const [loading, setLoading] = useState(false);
  const [mensagem, setMensagem] = useState(null);

  const API_URL = 'http://localhost:8080/api/alunos';
  const disciplinas = ['Matemática', 'Português', 'História', 'Geografia', 'Ciências'];

  useEffect(() => {
    carregarAlunos();
  }, []);

  const mostrarMensagem = (texto, tipo = 'success') => {
    setMensagem({ texto, tipo });
    setTimeout(() => setMensagem(null), 3000);
  };

  const carregarAlunos = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error('Erro ao carregar alunos');
      }

      const data = await response.json();
      setAlunos(data);
    } catch (error) {
      console.error('Erro:', error);
      mostrarMensagem('Erro ao conectar com o servidor. Verifique se o backend está rodando.', 'error');
    } finally {
      setLoading(false);
    }
  };

  const calcularMedia = (notasAluno) => {
    const soma = notasAluno.reduce((acc, nota) => acc + parseFloat(nota), 0);
    return soma / notasAluno.length;
  };

  const calcularMediaPorDisciplina = () => {
    if (alunos.length === 0) return [0, 0, 0, 0, 0];

    const medias = [0, 0, 0, 0, 0];
    for (let i = 0; i < 5; i++) {
      const soma = alunos.reduce((acc, aluno) => acc + parseFloat(aluno.notas[i]), 0);
      medias[i] = soma / alunos.length;
    }
    return medias;
  };

  const calcularMediaGeralTurma = () => {
    if (alunos.length === 0) return 0;
    const soma = alunos.reduce((acc, aluno) => acc + calcularMedia(aluno.notas), 0);
    return soma / alunos.length;
  };

  const getAlunosAcimaDaMedia = () => {
    const mediaTurma = calcularMediaGeralTurma();
    return alunos.filter(aluno => calcularMedia(aluno.notas) > mediaTurma);
  };

  const getAlunosFrequenciaBaixa = () => {
    return alunos.filter(aluno => parseFloat(aluno.frequencia) < 75);
  };

  const adicionarAluno = async () => {
    if (!nome.trim()) {
      mostrarMensagem('Por favor, insira o nome do aluno', 'error');
      return;
    }

    const notasValidas = notas.every(nota => {
      const n = parseFloat(nota);
      return !isNaN(n) && n >= 0 && n <= 10;
    });

    if (!notasValidas) {
      mostrarMensagem('Todas as notas devem estar entre 0 e 10', 'error');
      return;
    }

    const freq = parseFloat(frequencia);
    if (isNaN(freq) || freq < 0 || freq > 100) {
      mostrarMensagem('A frequência deve estar entre 0 e 100%', 'error');
      return;
    }

    const novoAluno = {
      nome: nome.trim(),
      notas: notas.map(n => parseFloat(n)),
      frequencia: parseFloat(frequencia)
    };

    try {
      setLoading(true);
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(novoAluno)
      });

      if (!response.ok) {
        throw new Error('Erro ao adicionar aluno');
      }

      const alunoAdicionado = await response.json();
      setAlunos([...alunos, alunoAdicionado]);

      setNome('');
      setNotas(['', '', '', '', '']);
      setFrequencia('');

      mostrarMensagem('Aluno adicionado com sucesso!', 'success');
    } catch (error) {
      console.error('Erro:', error);
      mostrarMensagem('Erro ao adicionar aluno. Tente novamente.', 'error');
    } finally {
      setLoading(false);
    }
  };

  const removerAluno = async (id) => {
    if (!window.confirm('Tem certeza que deseja remover este aluno?')) {
      return;
    }

    try {
      setLoading(true);
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE'
      });

      if (!response.ok) {
        throw new Error('Erro ao remover aluno');
      }

      setAlunos(alunos.filter(aluno => aluno.id !== id));
      mostrarMensagem('Aluno removido com sucesso!', 'success');
    } catch (error) {
      console.error('Erro:', error);
      mostrarMensagem('Erro ao remover aluno. Tente novamente.', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleNotaChange = (index, valor) => {
    const novasNotas = [...notas];
    novasNotas[index] = valor;
    setNotas(novasNotas);
  };

  const mediasPorDisciplina = calcularMediaPorDisciplina();
  const alunosAcimaDaMedia = getAlunosAcimaDaMedia();
  const alunosFrequenciaBaixa = getAlunosFrequenciaBaixa();

  return (
    <div className="container">
      {mensagem && (
        <div className={`alert ${mensagem.tipo === 'success' ? 'alert-success' : 'alert-error'}`}>
          {mensagem.texto}
        </div>
      )}

      <div className="header">
        <div className="header-content">
          <div>
            <h1>👥 Sistema de Notas - Professor Carlos</h1>
            <p>Gerencie as notas e frequência dos seus alunos</p>
          </div>
          <button onClick={carregarAlunos} disabled={loading} className="btn btn-secondary">
            🔄 Atualizar
          </button>
        </div>
      </div>

      <div className="grid grid-2">
        <div className="card">
          <h2>➕ Adicionar Aluno</h2>

          <div className="form-group">
            <label className="form-label">Nome do Aluno</label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              disabled={loading}
              className="form-input"
              placeholder="Digite o nome do aluno"
            />
          </div>

          <div className="form-group">
            <label className="form-label">Notas das Disciplinas (0 a 10)</label>
            {disciplinas.map((disciplina, index) => (
              <div key={index} className="disciplina-row">
                <span className="disciplina-label">{disciplina}:</span>
                <input
                  type="number"
                  step="0.1"
                  min="0"
                  max="10"
                  value={notas[index]}
                  onChange={(e) => handleNotaChange(index, e.target.value)}
                  disabled={loading}
                  className="form-input"
                  placeholder="0-10"
                />
              </div>
            ))}
          </div>

          <div className="form-group">
            <label className="form-label">Frequência (%)</label>
            <input
              type="number"
              step="0.1"
              min="0"
              max="100"
              value={frequencia}
              onChange={(e) => setFrequencia(e.target.value)}
              disabled={loading}
              className="form-input"
              placeholder="0-100"
            />
          </div>

          <button onClick={adicionarAluno} disabled={loading} className="btn btn-primary" style={{width: '100%'}}>
            {loading ? '⏳ Processando...' : '➕ Adicionar Aluno'}
          </button>
        </div>

        <div className="card">
          <h2>📋 Lista de Alunos ({alunos.length})</h2>

          <div className="list-container">
            {loading && alunos.length === 0 ? (
              <div className="loading-container">
                <div className="loading-spinner"></div>
                <p>Carregando alunos...</p>
              </div>
            ) : alunos.length === 0 ? (
              <div className="empty-state">Nenhum aluno cadastrado ainda</div>
            ) : (
              alunos.map((aluno) => (
                <div key={aluno.id} className="aluno-item">
                  <div className="aluno-header">
                    <div className="aluno-info">
                      <h3>{aluno.nome}</h3>
                      <div className="aluno-stats">
                        <p>
                          <span>Média:</span>{' '}
                          <span className="media-value">{calcularMedia(aluno.notas).toFixed(1)}</span>
                        </p>
                        <p>
                          <span>Frequência:</span>{' '}
                          <span className={parseFloat(aluno.frequencia) < 75 ? 'frequencia-baixa' : 'frequencia-alta'}>
                            {parseFloat(aluno.frequencia).toFixed(0)}%
                          </span>
                        </p>
                      </div>
                    </div>
                    <button
                      onClick={() => removerAluno(aluno.id)}
                      disabled={loading}
                      className="btn btn-danger"
                      title="Remover aluno"
                    >
                      🗑️
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      {alunos.length > 0 && (
        <div className="grid grid-2">
          <div className="card">
            <h2>📊 Média da Turma por Disciplina</h2>
            {disciplinas.map((disciplina, index) => (
              <div key={index} className="stats-row">
                <span className="stats-label">{disciplina}</span>
                <span className="stats-value">{mediasPorDisciplina[index].toFixed(1)}</span>
              </div>
            ))}
            <div className="stats-row">
              <span className="stats-label"><strong>Média Geral da Turma</strong></span>
              <span className="stats-total">{calcularMediaGeralTurma().toFixed(1)}</span>
            </div>
          </div>

          <div>
            <div className="alert-box alert-success-box">
              <h3>📈 Alunos Acima da Média da Turma</h3>
              {alunosAcimaDaMedia.length === 0 ? (
                <p className="empty-text">Nenhum aluno acima da média</p>
              ) : (
                <ul>
                  {alunosAcimaDaMedia.map((aluno) => (
                    <li key={aluno.id}>
                      • {aluno.nome} (média: {calcularMedia(aluno.notas).toFixed(1)})
                    </li>
                  ))}
                </ul>
              )}
            </div>

            <div className="alert-box alert-error-box">
              <h3>⚠️ Alunos com Frequência Abaixo de 75%</h3>
              {alunosFrequenciaBaixa.length === 0 ? (
                <p className="empty-text">Nenhum aluno com frequência baixa</p>
              ) : (
                <ul>
                  {alunosFrequenciaBaixa.map((aluno) => (
                    <li key={aluno.id}>
                      • {aluno.nome} (frequência: {parseFloat(aluno.frequencia).toFixed(0)}%)
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;