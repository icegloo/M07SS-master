#include <iostream>
#include <tchar.h>
#include "IRServeurUDP.h"
#include <string>
#include <fstream>
#include <stdio.h>
#include <sstream>
#include "ServeurDonneeDrone.h"


int main(){

    ServeurDonneeDrone serveurDonnee("LucasLeMeilleur", "3ZOBS");
    int n;

    do{
        Sleep(100);
        n = serveurDonnee.RecevoirDonneesDrone();
    }while(n>1);

    serveurDonnee.CloreJSON();
    std::cout <<" \nFin de recuperation des données... Appuyez sur entrée pour quitter";
    std::cin.get();
    return 0;
}
