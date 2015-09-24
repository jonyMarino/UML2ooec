/**********************************************************************************
 * Copyright: Spacebel SA
 * Project: MyModel
 * File: Package2/anObject.c
 * Code Management Tool File Version: 1.0.0
 * Date: $Date$
 * SDD Component: anObject
 * Language: C
 * Author: Johan Hardy
 * Last Change: $Id$
 **********************************************************************************/

/*!
 * \file Package2/anObject.c
 * \brief anObject
 */

/* Dependency with own header */
#include "Package1/Package2/anObject.h"
/* Dependency anObject_aClass: aClass <- anObject */
#include "Package1/Package2/aClass.h"

/**********************************************************************************
 * Package2/anObject.c
 **********************************************************************************/

/*## package Package1::Package2 */

/*## class TopLevel::anObject */

/*! 
 * \brief A public constant
 */
const int32 constant1 = -2005;

/*! 
 * \brief A public constant
 */
static const int32 constant2 = -2005;

/*! 
 * \var table
 * \brief A table
 */
uint32 table[MAX_SIZE] __attribute__ ((aligned(4)));

/*! 
 * \var var1
 * \brief Variable
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
volatile uint32 var1;

/*! 
 * \var var2
 * \brief Variable
 */
static uint32 var2;

/*! 
 * \var var3
 * \brief Variable typed with primitive type
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
signed long long var3;

/***************************************** FUNCTION *****************************************/
/*!
 * \brief Comment of function2
 *        
 * \return void : Nothing to return
 *        
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 *
 * <b>Function detailed processing extracted from source code</b>
 *
 ********************************************************************************************/
/*## operation function2(void) */
static void function2(void);

/*! 
 * \var reference
 */
static callback_t reference = function2;

/*## operation function1(void) */
void function1(void)
{
    /* Start of user code for function1 */
    /* End of user code for function1 */
}

/*## operation function2(void) */
static void function2(void)
{
    /* Start of user code for function2 */
    /* End of user code for function2 */
}

/*## operation function3(float64** const param1, const float64* param2, unsigned char param4) */
int32* function3(float64** const param1, const float64* param2, unsigned char param4)
{
    /* Start of user code for function3 */
    /* End of user code for function3 */
}

/**********************************************************************************
 * File Path: Package1/Package2/anObject.c
 **********************************************************************************/
