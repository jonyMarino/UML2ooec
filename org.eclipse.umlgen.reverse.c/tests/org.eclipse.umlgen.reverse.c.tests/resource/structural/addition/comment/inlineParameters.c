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
/* Mon Commentaire de Classe */
#include "vxWorks.h"
/* Ceci est un commentaire de include pour le define.h */
#include "define.h"

/** Signature de mon opération **/ static T_STATUS transformeRegTMenTC
   (
   UINT8 TM, /** Paramètre de Télémesure */
   UINT8 *TC /** Paramètre de Télécommande */
   );

/************************************************************************/
/* transformeRegTMenTC - ce service interne permet de transformer les bits */
/* de télémesure en bits de télécommande 								*/
/*																		*/
/* RETURNS : T_STATUS													*/
/*																		*/
/************************************************************************/
static T_STATUS transformeRegTMenTC
   (
   UINT8 TM, /** Paramètre de Télémesure */
   UINT8 *TC /** Paramètre de Télécommande */
   )
{
   /* initialisation du registre TC */
   *TC = 0x00;

   /* Transformation du bit de réchauffeur activé*/
   if (TM & C_SL_TM_HEATER)
      *TC |= C_SL_HEATER;
   /* Transformation du bit d'accès mémoire */
   if (TM & C_SL_TM_MODE)
      *TC |= C_SL_MODE;
   /* Transformation du bit de sécurité température des MAO */
   if (TM & C_SL_TM_SECU_MAO)
      *TC |= C_SL_SECU_MAO;

   /* récupération de la valeur du bit FORCE_RF_MAOSD */
   if (Val_FORCE_RF_MAOSD)
      *TC |= C_SL_FORCE_RF_MAOSD;
   /* récupération de la valeur du bit FORCE_RF_MAOC */
   if (Val_FORCE_RF_MAOC)
      *TC |= C_SL_FORCE_RF_MAOC;
   /* récupération de la valeur du bit de programmation des PLLs */
   if (!ProgrammationPLLAutorisee)
      *TC |= C_SL_INHIB_PLL;

   return SUCCES;
}
