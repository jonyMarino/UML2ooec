/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/
static T_STATUS acquisitionStatusSL
   (
   INT32 *Status, bool monbooleen, char ***c
   )
{
   UINT8 buffer[C_TAILLE_BUFFER_MAX];
   T_STATUS resultat;
   INT32 lg;

   /* mise de l'adresse de commande dans le buffer de communication */
   buffer[C_IDENTIFIANT] = C_ADR_STATUT_SL;
   buffer[C_OCTET1] = 0;
   buffer[C_OCTET2] = 0;

   /* emission de la requete */
   resultat = communication__emettreCommande (P_LASER,buffer,C_LG_TC_SL);
   if (resultat != SUCCES)
   {
      return resultat;
   }

   /* Renseignement sur la taille de la trame r�ponse */
   lg = C_LG_TM_SL;

   /* attente de la reponse */
   resultat = communication__attendreReponse (P_LASER,buffer,&lg);
   if (resultat != SUCCES)
   {
      return resultat;
   }

   /* recuperation du resultat */
   *Status = buffer[C_OCTET1];

   return SUCCES;
}