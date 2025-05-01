package com.flopez.core.data.mapper

import com.flopez.core.domain.model.CharacterQueryInfo
import com.flopez.core.network.model.CharacterQueryInfoDTO

fun CharacterQueryInfoDTO.toDomain() : CharacterQueryInfo {
    return CharacterQueryInfo(
        count = this.count,
        pages = this.pages,
        next = this.next,
        prev = this.prev
    )
}