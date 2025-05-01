package com.flopez.core.domain.exception


abstract class MortyException(msg: String) : Exception(msg)

/**Si hace falta más control sobre el tipo de error se pueden crear más subtipos de excepciones.
 * Por ejemplo se podria tener una NegativePageException, que fuera subtipo de QueryCharactersListException.
 * o un CharactersDoesNotExistException, subtipo de QuerySingleCharacterException.
 **/
sealed class CharacterQueryException(msg: String): MortyException(msg){
    data object QueryCharactersListException : CharacterQueryException("Could not query parameters from repository")
    data class QuerySingleCharacterException(val characterID: Long): CharacterQueryException("Could not query character for id=$characterID")
    data object InvalidCharacterId : CharacterQueryException("Could not load character id from savedStateHandle")
}