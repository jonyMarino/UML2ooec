
int monOperationC(int par1, char secondPar, INT32 Status);

int monOperationC(int par1, char secondPar, INT32 Status) {
	   /* mise de l'adresse de commande dans le buffer de communication */
	   buffer[C_IDENTIFIANT] = C_ADR_STATUT_SL;
	   buffer[C_OCTET1] = 0;
	   buffer[C_OCTET2] = 0;
}
