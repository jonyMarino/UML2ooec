/**********************************************************************************
 * Copyright: Spacebel SA
 * Project: MyModel
 * File: Package2/privateSingleton.h
 * Code Management Tool File Version: 1.0.0
 * Date: $Date$
 * SDD Component: privateSingleton
 * Language: C
 * Author: Johan Hardy
 * Last Change: $Id$
 **********************************************************************************/

/*!
 * \file Package2/privateSingleton.h
 * \brief privateSingleton
 */

#ifndef Package1_Package2_privateSingleton_H
#define Package1_Package2_privateSingleton_H

/* Dependency with parent package */
#include "Package1/Package2/Package2.h"


/**********************************************************************************
 * Package2/privateSingleton.h
 **********************************************************************************/

/*## package Package1::Package2 */

/*! 
 * \package privateSingleton
 */

/*## class TopLevel::privateSingleton */







/*!
 * \brief Type definition of the singleton class privateSingleton
 */
typedef struct privateSingleton_t
{
    /*! Variable */
    uint32 var1;
    /*! Property with upper bound != 1 */
    char buffer[20];
} privateSingleton_t;





#endif

/**********************************************************************************
 * File Path: Package1/Package2/privateSingleton.h
 **********************************************************************************/
