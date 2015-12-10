/**********************************************************************************
 * Copyright: Spacebel SA
 * Project: MyModel
 * File: Package2/anObject.h
 * Code Management Tool File Version: 1.0.0
 * Date: $Date$
 * SDD Component: anObject
 * Language: C
 * Author: Johan Hardy
 * Last Change: $Id$
 **********************************************************************************/

/*!
 * \file Package2/anObject.h
 * \brief anObject
 */

#ifndef Package1_Package2_anObject_H
#define Package1_Package2_anObject_H

/* Dependency with parent package */
#include "Package1/Package2/Package2.h"

/**********************************************************************************
 * Package2/anObject.h
 **********************************************************************************/

/*## package Package1::Package2 */

/*! 
 * \package anObject
 * \brief Comment of the object
 *
 * <b>Requirements traceability</b>
 *    - REQ_FUNC_001
 *    - REQ_FUNC_002
 */

/*## class TopLevel::anObject */

/*! 
 * \brief Constant MAX_SIZE
 */
#define MAX_SIZE (1024)

/*! 
 * \brief Callback definition
 */
typedef void (callback_t) (void);

/*! 
 * \brief A structure
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
typedef struct struct1_t
{
    /*! Comment of field1 */
    volatile int8 field1;
    /*! Comment of field2 */
    char* field2;
} struct1_t;

/*! 
 * \brief A structure
 */
typedef struct struct2_t
{
    /*! Comment of field1 */
    struct1_t field1;
    /*! Comment of field2 */
    char* field2;
    /*! Comment of field3 */
    struct struct1_t* field3;
} struct2_t;

/*! 
 * \brief An union
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
typedef union union1_t
{
    /*! Comment of field1 */
    int8 field1;
    /*! Comment of field2 */
    int8 field2;
    /*! Comment of field3 */
    int8 field3;
} union1_t;

/*! 
 * \brief A structure
 */
typedef struct struct3_t
{
    /*! Comment of field1 */
    int8 field1:2;
    /*! Comment of field2 */
    int8 field2:1;
    /*! Comment of field3 */
    int8 field3:5;
} struct3_t;

/*! 
 * \brief Comment of enum1
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
typedef enum enum1
{
    leteral1 = MAX_SIZE, /**< Comment of leteral1 */
    leteral2, /**< Comment of leteral2 */
    leteral3 = 255 /**< Comment of leteral3 */
} enum1;

/*! 
 * \brief Comment of enum1
 */
enum enum2
{
    leteral4 = MAX_SIZE /**< Comment of leteral4 */
};
typedef uint16 enum2;

/*! 
 * \brief A public constant
 */
extern const int32 constant1;

/*! 
 * \var table
 * \brief A table
 */
extern uint32 table[MAX_SIZE];

/*! 
 * \var var1
 * \brief Variable
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
extern volatile uint32 var1;

/*! 
 * \var var3
 * \brief Variable typed with primitive type
 *
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 */
extern signed long long var3;

/*! 
 * \var derived_var
 * \brief Derived variable (expect no declaration in the h file)
 */
extern double derived_var;

/************************************** INLINE FUNCTION *************************************/
/*!
 * \brief This is an inline operation
 *        
 * \param parameter1 [in]
 *        The arg of the inline operation
 *        
 * \return uint16 : The return value
 *        
 * <b>Function detailed processing extracted from source code</b>
 *
 ********************************************************************************************/
/*## operation MACRO(parameter1) */
#ifndef MACRO_REQUIRED
#define MACRO(parameter1) \
/* Start of user code for MACRO */ \
/* End of user code for MACRO */
#endif

/**************************************** ASM FUNCTION **************************************/
/*!
 * \brief Comment of function1
 *        
 * <b>Requirements traceability</b>
 *      - REQ_FUNC_001
 *
 * <b>Function detailed processing extracted from source code</b>
 *
 ********************************************************************************************/
/*## operation function1(void) */
void function1(void);

/***************************************** FUNCTION *****************************************/
/*!
 * \brief Comment of function2
 *        
 * \param param1 [inOut]
 *        The parameter1 comment
 *        
 * \param param2 [out]
 *        The parameter2 comment
 *        
 * \param param4 [in]
 *        The parameter4 comment
 *        
 * \return int32 : The return comment
 *        
 * <b>Function detailed processing extracted from source code</b>
 *
 ********************************************************************************************/
/*## operation function3(float64** const param1, const float64* param2, unsigned char param4) */
int32* function3(float64** const param1, const float64* param2, unsigned char param4);

#endif

/**********************************************************************************
 * File Path: Package1/Package2/anObject.h
 **********************************************************************************/
