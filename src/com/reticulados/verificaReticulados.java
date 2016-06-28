package com.reticulados;

public class verificaReticulados {

    private final int numNodos;

    private Integer[][] matrizPesos;
    private Integer[][] matrizElementoSuperiores;
    private Integer[][] matrizElementoInferiores;

    private int listaValorSup[];
    private int listaValorInf[];
    private int listaAdjacentesValor01[];
    private int listaAdjacentesValor02[];

    private int numeroVisitados = getNumNodos();

    private boolean[] visitados = new boolean[numeroVisitados];

    private int armazenaIteracaoElementoSup;
    private int armazenaIteracaoElementoInf;

    private int maior = 0;
    private int menor = 1000000;

    private int valorDeParada = 0;

    private int contadorIgualdade = 0;

    private int valorFinal = 0;

    private int contador01;
    private int contadorLigacoes01;
    private int contadorLigacoes02;

    private int contadorIgualdadeElementosMesmoNivel01 = 0;
    private int contadorIgualdadeElementosMesmoNivel02 = 0;

    private int armazenaValorListaAdjacente = 0;

    //Construtor
    public verificaReticulados(int numeroDeNodos) {

        setMatrizPesos(criaMatrizPesos(numeroDeNodos, 0));
        setMatrizElementoSuperiores(criaMatrizPesos(numeroDeNodos, 0));
        setMatrizElementoInferiores(criaMatrizPesos(numeroDeNodos, 0));
        numNodos = numeroDeNodos;
        this.visitados = new boolean[numeroDeNodos];

    }

    /*Método para criar a matriz vazia, para facilitar a programação no método construtor é passado a quantidade de vértices
     (numeroDeNodos), através disso é criado a matriz ("setMatrizPesos(criaMatrizPesos(numeroDeNodos, 0))").*/
    private Integer[][] criaMatrizPesos(int tamanho, Integer tipoLigacao) {

        Integer matriz[][] = new Integer[tamanho + 1][];

        for (int i = 0; i < tamanho; i++) {

            matriz[i] = new Integer[tamanho + 1];

            for (int j = 0; j < tamanho; j++) {

                matriz[i][j] = tipoLigacao;

            }

        }

        return matriz;

    }

    /*Método para inserir um vértice na matriz de adjacências, a inserção deve ser acompanhada do peso
     Ex: insereAresta(1, 1 ,-1) 1 ou insereAresta(1, 1 ,1), que indica se o grafo é direcinado ou não.*/
    public void insereAresta(int A, int B, int peso) {

        matrizPesos[A][B] = peso;
        matrizPesos[B][A] = peso;

    }

    public void limpaMatriz() {

        for (int i = 0; i < getNumNodos(); i++) {

            for (int j = 0; j < getNumNodos(); j++) {

                matrizElementoSuperiores[i][j] = 0;
                matrizElementoInferiores[i][j] = 0;

            }

        }

    }

    //Busca em profundidade, recebe o vértice inicial.
    public void identificaElementosSuperiores() {

        for (int i = 0; i < getNumNodos(); i++) {

            armazenaIteracaoElementoSup = i;
            elementoSuperior(i);

            for (int j = 0; j < getNumNodos(); j++) {

                visitados[j] = false;

            }

        }

    }

    private void elementoSuperior(int vertice) {

        //Marca o vértice passado no parâmetro como true.
        visitados[vertice] = true;

        //Enquando for verdade vai percorrer todos os vizinhos
        while (true) {

            matrizElementoSuperiores[armazenaIteracaoElementoSup][vertice] = vertice + 1;

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizPesos[vertice][i] != 0) {

                    //Se não tiver sido visitado e a interação for maior que o vertice avaliado
                    if (!visitados[i] && i > vertice) {

                        //Chama o método novamente e passa o vértice que não foi visitado como parâmetro.
                        elementoSuperior(i);

                    }

                }

            }

            /*Se o vértice tiver sido visitado, ou seja, se todos os vértices tiverem sido visitados,
             o comando break entra em ação.*/
            break;

        }

    }

    public void identificaElementosInferiores() {

        for (int i = 0; i < getNumNodos(); i++) {

            armazenaIteracaoElementoInf = i;
            elementoInferior(i);

            for (int j = 0; j < getNumNodos(); j++) {

                visitados[j] = false;

            }

        }

    }

    private void elementoInferior(int vertice) {

        //Marca o vértice passado no parâmetro como true.
        visitados[vertice] = true;

        //Enquando for verdade vai percorrer todos os vizinhos.
        while (true) {

            //Insere na matriz de Elementos inferiores.
            matrizElementoInferiores[armazenaIteracaoElementoInf][vertice] = vertice + 1;

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizPesos[i][vertice] != 0) {

                    //Se não tiver sido visitadoe for menor que o vértice visita
                    if (!visitados[i] && i < vertice) {

                        //Chama o método novamente e passa o vértice que não foi visitado como parâmetro.
                        elementoInferior(i);

                    }

                }

            }

            /*Se o vértice tiver sido visitado, ou seja, se todos os vértices tiverem sido visitados,
             o comando break entra em ação.*/
            break;

        }

    }

    private void identificaMaximaFronteira(int verifica, int vetor[], int valor1, int valor2) {

        int auxiliar;
        maior = 0;

        for (int x = 0; x < verifica; x++) {

            auxiliar = vetor[x];

            if (maior < auxiliar) {

                maior = vetor[x];

            }

        }

        System.out.print("Maior Elemento da fronteira inferior de " + (valor1 + 1) + " e " + (valor2 + 1) + " : " + maior);
        System.out.print("\n\n");

    }

    private void identificaMinimaFronteira(int verifica, int vetor[], int valor1, int valor2) {

        int auxiliar;

        for (int x = 0; x < verifica; x++) {

            if (vetor[x] != 0) {

                auxiliar = vetor[x];

                if (menor > auxiliar) {

                    menor = vetor[x];

                }

            }

        }

        System.out.println("Menor Elemento da fronteira superior de " + (valor1 + 1) + " e " + (valor2 + 1) + " : " + menor);
        System.out.print("\n");

    }

    private void ListaAdjacencia(int vertice, int primeiroOuSegundo) {

        int cont = 0;

        if (primeiroOuSegundo == 1) {

            listaAdjacentesValor01 = new int[getNumNodos()];

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizPesos[vertice][i] == 1) {

                    listaAdjacentesValor01[cont] = (i + 1);
                    cont++;

                }

            }

        } else {

            listaAdjacentesValor02 = new int[getNumNodos()];

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizPesos[vertice][i] == 1) {

                    listaAdjacentesValor02[cont] = (i + 1);
                    cont++;

                }

            }

        }

    }

    public void identificaFronteiraSuperiorMinima(int valor01, int valor02) {

        listaValorSup = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        armazenaValorListaAdjacente = 0;

        for (int i = 0; i < getNumNodos(); i++) {

            listaValorSup[i] = 0;

        }

        //for para contar quantos se ligam com elemento valor1
        for (int i = 0; i < getNumNodos(); i++) {

            if (matrizElementoSuperiores[valor01][i] != 0 && matrizElementoSuperiores[valor01][i] != (valor01 + 1)) {

            }

        }

        //for para contar quantos se ligam com elemento valor2
        for (int i = 0; i < getNumNodos(); i++) {

            if (matrizElementoSuperiores[valor02][i] != 0 && matrizElementoSuperiores[valor02][i] != (valor02 + 1)) {

            }

        }

        for (int i = 0; i < getNumNodos(); i++) {

            if ((matrizElementoSuperiores[valor01][i] != 0) && (matrizElementoSuperiores[valor01][i] == matrizElementoSuperiores[valor02][i])) {

                listaValorSup[contadorIgualdade] = matrizElementoSuperiores[valor01][i];
                contadorIgualdade++;

            }

        }

        if (contadorIgualdade == 0) {

            System.out.println("Não existe fronteira superior para os elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ".\n");

        } else {

            System.out.print("Fronteira superior dos elementos " + (valor01 + 1) + " e " + (valor02 + 1) + " : ");

            for (int k = 0; k < contadorIgualdade; k++) {

                if (listaValorSup[k] != 0) {

                    System.out.print(listaValorSup[k]);

                }

            }

            System.out.print("\n");

            ListaAdjacencia(valor01, 1);
            ListaAdjacencia(valor02, 2);

            for (int j = 0; j < getNumNodos(); j++) {

                armazenaValorListaAdjacente = 0;

                if (listaAdjacentesValor01[j] > (valor01 + 1) && listaAdjacentesValor01[j] > (valor02 + 1)) {

                    armazenaValorListaAdjacente = listaAdjacentesValor01[j];

                }

                if (armazenaValorListaAdjacente != 0) {

                    for (int i = 0; i < getNumNodos(); i++) {

                        if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                            contadorIgualdadeElementosMesmoNivel01++;

                        }

                    }

                }

            }

            if (contadorIgualdadeElementosMesmoNivel01 == 2) {

                System.out.println("Não possui fronteira superior mínima.");

            } else {

                identificaMinimaFronteira(contadorIgualdade, listaValorSup, valor01, valor02);

            }

        }

    }

    public void identificaFronteiraInferiorMaxima(int valor01, int valor02) {

        listaValorInf = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        armazenaValorListaAdjacente =0;

        for (int i = 0; i < getNumNodos(); i++) {

            listaValorInf[i] = 0;

        }

        //for para contar quantos se ligam com elemento valor1
        for (int i = 0; i < getNumNodos(); i++) {

            if (matrizElementoInferiores[valor01][i] != 0 && matrizElementoInferiores[valor01][i] != (valor01 + 1)) {

            }

        }

        //for para contar quantos se ligam com elemento valor2
        for (int i = 0; i < getNumNodos(); i++) {

            if (matrizElementoInferiores[valor02][i] != 0 && matrizElementoInferiores[valor02][i] != (valor02 + 1)) {

            }

        }

        for (int i = 0; i < getNumNodos(); i++) {

            if ((matrizElementoInferiores[valor01][i] != 0) && (matrizElementoInferiores[valor01][i] == matrizElementoInferiores[valor02][i])) {

                listaValorInf[contadorIgualdade] = matrizElementoInferiores[valor01][i];
                contadorIgualdade++;

            }

        }

        if (contadorIgualdade == 0) {

            System.out.println("Não existe fronteira inferior para os elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ".\n");

        } else {

            System.out.print("Fronteira inferior dos elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ": ");

            for (int k = 0; k < contadorIgualdade; k++) {

                if (listaValorInf[k] != 0) {

                    System.out.print(listaValorInf[k]);

                }

            }

            System.out.print("\n");

            ListaAdjacencia(valor01, 1);
            ListaAdjacencia(valor02, 2);

            for (int j = 0; j < getNumNodos(); j++) {

                armazenaValorListaAdjacente = 0;

                if (listaAdjacentesValor01[j] < (valor01 + 1) && listaAdjacentesValor01[j] < (valor02 + 1) && listaAdjacentesValor01[j] != 0 && listaAdjacentesValor01[j] != 0) {

                    armazenaValorListaAdjacente = listaAdjacentesValor01[j];

                }

                if (armazenaValorListaAdjacente != 0) {

                    for (int i = 0; i < getNumNodos(); i++) {

                        if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                            contadorIgualdadeElementosMesmoNivel01++;

                        }

                    }

                }

            }

            if (contadorIgualdadeElementosMesmoNivel01 == 2) {

                System.out.println("Não possui fronteira inferior máxima.");

            } else {

                identificaMaximaFronteira(contadorIgualdade, listaValorInf, valor01, valor02);

            }

        }

    }

    private void complementoVerificaReticulado(int valor01, int valor02) {

        listaValorInf = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        contadorIgualdadeElementosMesmoNivel02 = 0;

        ListaAdjacencia(valor01, 1);
        ListaAdjacencia(valor02, 2);

        for (int j = 0; j < getNumNodos(); j++) {

            armazenaValorListaAdjacente = 0;

            if (listaAdjacentesValor01[j] < (valor01 + 1) && listaAdjacentesValor01[j] < (valor02 + 1) && listaAdjacentesValor01[j] != 0 && listaAdjacentesValor01[j] != 0) {

                armazenaValorListaAdjacente = listaAdjacentesValor01[j];

            }

            if (armazenaValorListaAdjacente != 0) {

                for (int i = 0; i < getNumNodos(); i++) {

                    if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                        contadorIgualdadeElementosMesmoNivel01++;

                    }

                }

            }

        }

        ListaAdjacencia(valor01, 1);
        ListaAdjacencia(valor02, 2);

        for (int j = 0; j < getNumNodos(); j++) {

            armazenaValorListaAdjacente = 0;

            if (listaAdjacentesValor01[j] > (valor01 + 1) && listaAdjacentesValor01[j] > (valor02 + 1)) {

                armazenaValorListaAdjacente = listaAdjacentesValor01[j];

            }

            if (armazenaValorListaAdjacente != 0) {

                for (int i = 0; i < getNumNodos(); i++) {

                    if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                        contadorIgualdadeElementosMesmoNivel02++;

                    }

                }

            }

        }

        if (contadorIgualdadeElementosMesmoNivel01 == 2 || contadorIgualdadeElementosMesmoNivel02 == 2) {

            valorDeParada = 1;

        }

    }

    public void verificaReticulado() {

        valorFinal = getNumNodos();
        contador01 = 0;
        contadorLigacoes01 = 0;
        contadorLigacoes02 = 0;
        valorDeParada = 0;

        //for para verificar se todos elementos pode ir até o último elemento
        for (int i = 0; i < valorFinal; i++) {

            if (matrizElementoSuperiores[i][valorFinal - 1] == getNumNodos()) {

                contadorLigacoes01++;

            }

        }

        for (int i = 0; i < valorFinal; i++) {

            if (matrizElementoInferiores[i][0] == 1) {

                contadorLigacoes02++;

            }

        }

        // Se o primeiro elemento puder chegar em todos elementos, e o ultimo elemento puder chegar em todos elementos, continua..
        if ((contadorLigacoes01 == valorFinal) && (contadorLigacoes02 == valorFinal)) {

            for (int i = 0; i < valorFinal; i++) {

                for (int j = 0; j < valorFinal; j++) {

                    if ((contador01 != i) && (contador01 > i)) {

                        if (valorDeParada != 1) {

                            complementoVerificaReticulado(i, contador01);

                        }

                    }

                    contador01++;

                }

                contador01 = 0;

            }

            if (valorDeParada != 1) {

                System.out.print("\n");
                System.out.println("É reticulado.");

            } else {

                System.out.print("\n");
                System.out.println("Não é reticulado, pois não existe fronteira inferior máxima nem fronteira superior mínima em todos os pares de elementos.");

            }

        } else {

            System.out.print("\n");
            System.out.println("Não é reticulado, pois não possui limite mínimo ou limite máximo.");

        }

    }

    private void setMatrizPesos(Integer[][] pesos) {

        this.matrizPesos = pesos;

    }

    private void setMatrizElementoSuperiores(Integer[][] zero) {

        this.matrizElementoSuperiores = zero;

    }

    private void setMatrizElementoInferiores(Integer[][] zero) {

        this.matrizElementoInferiores = zero;

    }

    private int getNumNodos() {

        return numNodos;

    }

}
