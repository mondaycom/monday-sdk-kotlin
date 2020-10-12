package com.monday.graphql

class Mutation {
    public var name: String? = null
    public var rootField: Field? = null

    public class Builder {
        private var name: String? = null
        private var rootField: Field? = null

        fun withRootField(name: String): Mutation.Builder {
            return withRootField(name) { }
        }

        fun withRootField(name: String, block: (Field.Builder) -> Unit): Mutation.Builder {
            val fieldBuilder = Field.Builder().withName(name)
            fieldBuilder.apply(block)
            rootField = fieldBuilder.build()
            return this
        }

        fun withRootField(field: Field): Mutation.Builder {
            rootField = field
            return this
        }

        fun withName(name: String): Builder {
            this.name = name
            return this
        }

        fun build() =
            Mutation().apply {
                name = this@Builder.name
                rootField = this@Builder.rootField
            }
    }

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append("mutation ")

        name?.let {
            builder.append("$it ")
        }

        builder.append("{ ")
        builder.append(rootField!!.asString())
        if (rootField!!.selectedFields.isEmpty())
            builder.append("{ }")
        builder.append(" }")

        return builder.toString()
    }
}