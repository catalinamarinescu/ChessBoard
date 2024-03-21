*******************************************************************************

                                Proiect PA 2023
                                Placeholder Name
                                   -Etapa 1-

---------------------------Instructiuni de compilare---------------------------
    
-> Compilarea programului: make build
-> Executarea programului: make run
-> Stergerea executabilului: make clean            

-----------------------------Structura proiectului-----------------------------

-> Commands:
    Directorul acesta contine clase cu toate tipurile de comenzi ce pot fi
date de catre XBboard in timpul jocului de sah (force, go, move, new, quit,
resign). Ele sunt grupate in interfata Action.

-> Logic:
    Acesta este directorul principal, ce contine atat mainul, cat si clasele
cele mai semnificative implementarii logicii jocului de sah crazy house.
Cateva dintre cele mai interesante clase sunt:
    -> Board: reprezinta implementarea tablei de joc sub forma unui vector
              bidimensional.
    -> Bot: este un pseudobot, intrucat face mutari aleatorii pentru testarea
            functionalitatii programului.
    -> GetMovesOfPiece: aici se calculeaza toate miscarile posibile si legale
                        pentru fiecare piesa de pe tabla. Mai ales se tine cont
                        daca regele este in sah sau nu.
    -> Main: se primesc comenzile de input si se dau raspunsuri conform lor.
            Programul este optimizat sa functioneze impreuna cu XBoard.

-> Moves:
    Contine decodificarea mutarilor trimise in formatul san=0 (ex. e2e4).
In momentul efectuarii unei mutari, se va lua piesa de pe pozitia curenta si
se va pune pe pozitia dorita.

-----------------------------Abordarea algoritmica-----------------------------

    Pentru aceasta etapa, cum nu a fost folosit vreun algortim de calcul al
mutarilor pentru botul de sah, programul nu are o complexitate mare.
-> Complexitate: O(1).


------------------------------Surse de inspiratie------------------------------

    Alti boti de sah de pe internet, precum:
-> https://github.com/roxanastiuca/Chess-Engine

-------------------------------Responsabilitati--------------------------------

-> Ionita Cosmin: Implementarea tablei de sah, alaturi de botul random.
-> Gheorghisor Ileana: Implementarea comenzilor pentru feature-urile XBoard
alaturi de Readme.
-> Marinescu Catalina
        si
-> Popescu Ioana: Implementarea comenzilor legate de jocul propriu-zis,
alaturi de logica din spatele Rocadelor si En passant.


*******************************************************************************