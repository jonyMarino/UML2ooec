static acquisitionStatusSLC
   (
   INT32 *Status
   )
{
   UINT8 buffer[C_TAILLE_BUFFER_MAX];
   T_STATUS resultat;
   INT32 lg;

   /* mise de l'adresse de commande dans le buffer de communication */
   buffer[C_IDENTIFIANT] = C_ADR_STATUT_SL;
   buffer[C_OCTET1] = 0;
   buffer[C_OCTET2] = 0;
}
