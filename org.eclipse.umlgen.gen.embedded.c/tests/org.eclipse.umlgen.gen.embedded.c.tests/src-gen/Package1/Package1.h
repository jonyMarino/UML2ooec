/**********************************************************************************
 * Copyright: Spacebel SA
 * Project: MyModel
 * File: Package1/Package1.h
 * Code Management Tool File Version: 1.0.0
 * Date: $Date$
 * SDD Component: Package1
 * Language: C
 * Author: Johan Hardy
 * Last Change: $Id$
 **********************************************************************************/

/*!
 * \file Package1/Package1.h
 * \brief Package1
 */

#ifndef Package1_Package1_H
#define Package1_Package1_H


/* Dependency Software_external_lib: "external_lib.h" <- Package1 */
#include "external_lib.h"

/**********************************************************************************
 * Package1/Package1.h
 **********************************************************************************/

/*## package Package1 */

/*! 
 * \package Package1
 * \brief Comment of Package1
 *
 * <b>Requirements traceability</b>
 *    - REQ_FUNC_002
 */

/*! 
 * \brief NULL value.
 */
#ifndef NULL
#define NULL (0)
#endif

/*! 
 * \brief NULL pointer value.
 */
#define NULLPTR ((void *) 0)

/*! 
 * \brief Tick frequency (in Hz).
 */
#ifdef TICK_FREQUENCY
#define TICK_FREQUENCY (1000000U/TICK_PERIOD_IN_MICROSECONDS)
#endif

/*! 
 * \brief Signed integer 8 bits.
 */
typedef signed char int8;

/*! 
 * \brief Signed integer 16 bits.
 */
typedef signed short int16;

/*! 
 * \brief Signed integer 32 bits.
 */
typedef signed int int32;

/*! 
 * \brief Signed integer 64 bits.
 */
typedef signed long long int64;

/*! 
 * \brief Unsigned integer 8 bits.
 */
typedef unsigned char uint8;

/*! 
 * \brief Unsigned integer 16 bits.
 */
typedef unsigned short uint16;

/*! 
 * \brief Unsigned integer 32 bits.
 */
typedef unsigned int uint32;

/*! 
 * \brief Unsigned integer 64 bits.
 */
typedef unsigned long long uint64;

/*! 
 * \brief Floating point value, 32 bits.
 */
typedef float float32;

/*! 
 * \brief Floating point value, 64 bits.
 */
typedef double float64;

/*! 
 * \brief Boolean type.
 */
enum Boolean
{
    False = 0, /**< Logical False */
    True = 1U /**< Logical True */
};
typedef uint8 Boolean;

/*! 
 * \brief Error codes returned by the software. These error codes are defined in an enumeration.
 */
typedef uint16 error_code_t;

#endif

/**********************************************************************************
 * File Path: Package1/Package1.h
 **********************************************************************************/
