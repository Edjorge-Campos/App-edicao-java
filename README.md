# App de edição em Java
Editor de imagens em tons de cinzento desenvolvido em Java, o meu primeiro projeto de programação, realizado na cadeira de Introdução à Programação do ISCTE.
Este foi o meu primeiro projeto de programação alguma vez. É também uma das minhas primeiras vezes a escrever código, e o objetivo era implementar um conjunto de filtros e operações de manipulação de imagem do zero, trabalhando diretamente com matrizes de pixéis.

# Demonstração



https://github.com/user-attachments/assets/f66f576e-9f9e-4edc-80ea-415f7b572238





# Funcionalidades

Filtros — aplicados a cada pixel individualmente:

-Inverter
-Escurecer / Clarear
-Contraste
-Grão


# Efeitos — aplicados à imagem inteira:

-Vignette
-Margem
-Espelhar horizontalmente / verticalmente
-Grid
-Linhas
-Antigo (combinação de grão, vignette e margem)
-Retro (combinação de contraste, vignette e desfoque)


# Operações — transformações que alteram a estrutura da imagem:

-Cortar para quadrado
-Escurecer área selecionada
-Crop
-Expandir
-Rodar 90º
-Desfocar (blur por média de vizinhança)
-Posterizar
-Copiar / Cortar / Colar seleção

# Como funciona

Cada imagem é representada como uma matriz bidimensional de inteiros (int[][]), onde cada valor corresponde ao tom de cinzento de um pixel, entre 0 (preto) e 255 (branco). Todas as operações manipulam esta matriz diretamente.


# Tecnologias

Java
Framework Greyditor (fornecida pelo ISCTE)


# Melhorias futuras

Implementar um sistema de undo com imagemAnterior
Adicionar suporte a imagens coloridas (RGB)
Permitir guardar e carregar imagens diretamente pela interface



# Contexto académico

Projeto desenvolvido na cadeira de Introdução à Programação do 1º ano de Engenharia Informática no ISCTE — Instituto Universitário de Lisboa.


Autor

Edjorge Campos
1º ano — Engenharia Informática, ISCTE
Interesse em arquitetura de computadores e sistemas funcionais
